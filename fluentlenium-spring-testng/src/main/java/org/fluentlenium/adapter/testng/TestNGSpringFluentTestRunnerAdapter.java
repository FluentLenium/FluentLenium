package org.fluentlenium.adapter.testng;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;
import org.fluentlenium.adapter.DefaultSharedMutator;
import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.adapter.SharedMutator.EffectiveParameters;
import org.fluentlenium.adapter.ThreadLocalFluentControlContainer;
import org.fluentlenium.adapter.exception.AnnotationNotFoundException;
import org.fluentlenium.adapter.exception.MethodNotFoundException;
import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriver;
import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.WebDrivers;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.inject.ContainerContext;
import org.fluentlenium.core.inject.ContainerFluentControl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * FluentLenium Test Runner Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your Test class.
 */
@SuppressWarnings("PMD.GodClass")
class TestNGSpringFluentTestRunnerAdapter extends TestNGSpringFluentControlImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestNGSpringFluentTestRunnerAdapter.class);

    private final SharedMutator sharedMutator;

    private static final ThreadLocal<EffectiveParameters<?>> PARAMETERS_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<String> TEST_METHOD_NAME = new ThreadLocal<>();
    private static final ThreadLocal<Class<?>> TEST_CLASS = new ThreadLocal<>();

    /**
     * Creates a new test runner adapter.
     */
    public TestNGSpringFluentTestRunnerAdapter() {
        super(new ThreadLocalFluentControlContainer());
        this.sharedMutator = new DefaultSharedMutator();
    }

    /**
     * Invoked when a test class has finished (whatever the success of failing status)
     *
     * @param testClass test class to terminate
     */
    public static void afterClass(Class<?> testClass) {
        List<SharedWebDriver> sharedWebDrivers = SharedWebDriverContainer.INSTANCE.getTestClassDrivers(testClass);
        sharedWebDrivers.forEach(SharedWebDriverContainer.INSTANCE::quit);
    }

    /**
     * @return Class of currently running test
     */
    protected Class<?> getTestClass() {
        Class<?> currentTestClass = TestNGSpringFluentTestRunnerAdapter.TEST_CLASS.get();
        if (currentTestClass == null) {
            LOGGER.warn("Current test class is null. Are you in test context?");
        }
        return currentTestClass;
    }

    /**
     * @return method name (as String) of currently running test
     */
    protected String getTestMethodName() {
        String currentTestMethodName = TestNGSpringFluentTestRunnerAdapter.TEST_METHOD_NAME.get();
        if (currentTestMethodName == null) {
            LOGGER.warn("Current test method name is null. Are you in text context?");
        }
        return currentTestMethodName;
    }

    /**
     * Allows to access Class level annotation of currently running test
     *
     * @param annotation interface you want to access
     * @param <T>        the class annotation
     * @return Annotation instance
     * @throws AnnotationNotFoundException when annotation you want to access couldn't be find
     */
    protected <T extends Annotation> T getClassAnnotation(Class<T> annotation) {
        T definedAnnotation = getTestClass().getAnnotation(annotation);

        if (definedAnnotation == null) {
            throw new AnnotationNotFoundException();
        }

        return definedAnnotation;
    }

    /**
     * Allows to access method level annotation of currently running test
     *
     * @param annotation interface you want to access
     * @param <T>        the method annotation
     * @return Annotation instance
     * @throws AnnotationNotFoundException of annotation you want to access couldn't be found
     * @throws MethodNotFoundException     if test method couldn't be found - if it occurs that's most likely FL bug
     */
    protected <T extends Annotation> T getMethodAnnotation(Class<T> annotation) {
        T definedAnnotation;
        try {
            definedAnnotation = getTestClass().getDeclaredMethod(getTestMethodName()).getAnnotation(annotation);
        } catch (NoSuchMethodException e) {
            throw new MethodNotFoundException(e);
        }

        if (definedAnnotation == null) {
            throw new AnnotationNotFoundException();
        }

        return definedAnnotation;
    }

    /**
     * Invoked when a test method is starting.
     *
     * @param testClass Test class
     * @param testName  Test name
     */
    protected void starting(Class<?> testClass, String testName) {
        PARAMETERS_THREAD_LOCAL.set(sharedMutator.getEffectiveParameters(testClass, testName,
                getDriverLifecycle()));

        SharedWebDriver sharedWebDriver;

        try {
            sharedWebDriver = getSharedWebDriver(PARAMETERS_THREAD_LOCAL.get());
        } catch (ExecutionException | InterruptedException e) {
            this.failed(null, testClass, testName);

            String causeMessage = this.getCauseMessage(e);

            throw new WebDriverException("Browser failed to start, test [ " + testName + " ] execution interrupted."
                    + (isEmpty(causeMessage) ? "" : "\nCaused by: [ " + causeMessage + "]"), e);
        }

        initFluent(sharedWebDriver.getDriver());
    }

    private void setTestClassAndMethodValues() {
        Optional.ofNullable(PARAMETERS_THREAD_LOCAL.get()).ifPresent((effectiveParameters) -> {
            Optional.ofNullable(effectiveParameters.getTestClass()).ifPresent(TEST_CLASS::set);
            Optional.ofNullable(effectiveParameters.getTestName()).ifPresent(this::setMethodName);
        });
    }

    private void setMethodName(String methodName) {
        String className = StringUtils.substringBefore(methodName, "(");
        TEST_METHOD_NAME.set(className);
    }

    private String getCauseMessage(Exception e) {
        String causeMessage = null;
        Throwable cause = e;
        while (true) {
            if (cause.getCause() == null || cause.getCause() == cause) {
                break;
            } else {
                cause = cause.getCause();
                if (cause.getLocalizedMessage() != null) {
                    causeMessage = cause.getLocalizedMessage();
                }
            }
        }
        return causeMessage;
    }

    private SharedWebDriver getSharedWebDriver(EffectiveParameters<?> parameters)
            throws ExecutionException, InterruptedException {
        return getSharedWebDriver(parameters, null);
    }

    /**
     * Returns SharedDriver instance
     *
     * @param parameters        driver parameters
     * @param webDriverExecutor executor service
     * @return SharedDriver
     * @throws ExecutionException   execution exception
     * @throws InterruptedException interrupted exception
     */
    protected SharedWebDriver getSharedWebDriver(EffectiveParameters<?> parameters,
                                                 ExecutorService webDriverExecutor)
            throws ExecutionException, InterruptedException {
        SharedWebDriver sharedWebDriver = null;
        ExecutorService executorService = getExecutor(webDriverExecutor);

        for (int retryCount = 0; retryCount < getBrowserTimeoutRetries(); retryCount++) {

            Future<SharedWebDriver> futureWebDriver = createDriver(parameters, executorService);
            shutDownExecutor(executorService);

            try {
                sharedWebDriver = futureWebDriver.get();
            } catch (InterruptedException | ExecutionException e) {
                executorService.shutdownNow();
                throw e;
            }

            if (sharedWebDriver != null) {
                break;
            }
        }

        setTestClassAndMethodValues();
        return sharedWebDriver;
    }

    private void shutDownExecutor(ExecutorService executorService) throws InterruptedException {
        executorService.shutdown();
        if (didNotExitGracefully(executorService)) {
            executorService.shutdownNow();
        }
    }

    private boolean didNotExitGracefully(ExecutorService executorService) throws InterruptedException {
        return !executorService.awaitTermination(getBrowserTimeout(), TimeUnit.MILLISECONDS);
    }

    private Future<SharedWebDriver> createDriver(EffectiveParameters<?> parameters, ExecutorService executorService) {
        return executorService.submit(
                () -> SharedWebDriverContainer.INSTANCE.getOrCreateDriver(this::newWebDriver, parameters));
    }

    private ExecutorService getExecutor(ExecutorService webDriverExecutor) {
        if (webDriverExecutor == null) {
            return Executors.newSingleThreadExecutor();
        }
        return webDriverExecutor;
    }

    private void clearThreadLocals() {
        PARAMETERS_THREAD_LOCAL.remove();
        TEST_CLASS.remove();
        TEST_METHOD_NAME.remove();
    }

    /**
     * Invoked when a test method has finished (whatever the success of failing status)
     *
     * @param testClass Test class
     * @param testName  Test name
     */
    protected void finished(Class<?> testClass, String testName) {
        DriverLifecycle driverLifecycle = getDriverLifecycle();
        EffectiveParameters<?> parameters = sharedMutator.getEffectiveParameters(testClass, testName,
                driverLifecycle);
        SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE.getDriver(parameters);

        if (driverLifecycle == DriverLifecycle.METHOD || driverLifecycle == DriverLifecycle.THREAD) {
            Optional.ofNullable(sharedWebDriver).ifPresent(SharedWebDriverContainer.INSTANCE::quit);
        }

        if (getDeleteCookies()) {
            Optional.ofNullable(sharedWebDriver).ifPresent(shared -> shared.getDriver().manage().deleteAllCookies());
        }

        clearThreadLocals();
        releaseFluent();
    }

    /**
     * Invoked when a test method has failed (before finished)
     *
     * @param e         Throwable thrown by the failing test.
     * @param testClass Test class
     * @param testName  Test name
     */
    protected void failed(Throwable e, Class<?> testClass, String testName) {
        if (isFluentControlAvailable() && !isIgnoredException(e)) {
            try {
                if (getScreenshotMode() == TriggerMode.AUTOMATIC_ON_FAIL && canTakeScreenShot()) {
                    this.takeScreenshot(testClass.getSimpleName() + "_" + testName + ".png");
                }
            } catch (Exception exception) { // NOPMD EmptyCatchBlock
                // Can't write screenshot, for some reason.
            }

            try {
                if (getHtmlDumpMode() == TriggerMode.AUTOMATIC_ON_FAIL && getDriver() != null) {
                    takeHtmlDump(testClass.getSimpleName() + "_" + testName + ".html");
                }
            } catch (Exception exception) { // NOPMD EmptyCatchBlock
                // Can't write htmldump, for some reason.
            }

        }
    }

    @Override
    public final WebDriver getDriver() {
        return getFluentControl() == null ? null : getFluentControl().getDriver();
    }

    /**
     * Load a {@link WebDriver} into this adapter.
     * <p>
     * This method should not be called by end user.
     *
     * @param webDriver webDriver to use.
     * @throws IllegalStateException when trying to register a different webDriver that the current one.
     */
    public void initFluent(WebDriver webDriver) {
        if (webDriver == null) {
            releaseFluent();
            return;
        }

        if (getFluentControl() != null) {
            if (getFluentControl().getDriver() == webDriver) {
                return;
            }
            if (getFluentControl().getDriver() != null) {
                throw new IllegalStateException("Trying to init a WebDriver, but another one is still running");
            }
        }

        ContainerFluentControl adapterFluentControl = new ContainerFluentControl(new FluentDriver(webDriver, this, this));
        setFluentControl(adapterFluentControl);
        ContainerContext context = adapterFluentControl.inject(this);
        adapterFluentControl.setContext(context);
    }

    /**
     * Release the current {@link WebDriver} from this adapter.
     * <p>
     * This method should not be called by end user.
     */
    public void releaseFluent() {
        if (getFluentControl() != null) {
            ((FluentDriver) getFluentControl().getAdapterControl()).releaseFluent();
            setFluentControl(null);
        }
    }

    /**
     * Creates a new {@link WebDriver} instance.
     * <p>
     * This method should not be called by end user, but may be overriden if required.
     * <p>
     * Before overriding this method, you should consider using {@link WebDrivers} registry and configuration
     * {@link ConfigurationProperties#getWebDriver()}.
     * <p>
     * To retrieve the current managed {@link WebDriver}, call {@link #getDriver()} instead.
     *
     * @return A new WebDriver instance.
     * @see #getDriver()
     */
    public WebDriver newWebDriver() {
        WebDriver webDriver = WebDrivers.INSTANCE.newWebDriver(getWebDriver(), getCapabilities(), this);
        if (Boolean.TRUE.equals(getEventsEnabled())) {
            webDriver = new EventFiringWebDriver(webDriver);
        }
        return webDriver;
    }

    /**
     * Checks if the exception should be ignored and not reported as a test case fail
     *
     * @param e - the exception to check is it defined in ignored exceptions set
     * @return boolean
     */
    boolean isIgnoredException(Throwable e) {
        boolean isIgnored = false;
        if (e != null) {
            Class<?> clazz = e.getClass();
            do {
                if (IGNORED_EXCEPTIONS.contains(clazz.getName())) {
                    isIgnored = true;
                    break;
                }
                clazz = clazz.getSuperclass();
            } while (clazz != Object.class);
        }

        return isIgnored;
    }

    private static final Set<String> IGNORED_EXCEPTIONS = ImmutableSet.of(
            "org.junit.AssumptionViolatedException",
            "org.junit.internal.AssumptionViolatedException",
            "org.testng.SkipException");

    public ContainerFluentControl getFluentControl() {
        FluentControlContainer fluentControlContainer = getControlContainer();

        if (fluentControlContainer == null) {
            throw new IllegalStateException("FluentControl is not initialized, WebDriver or Configuration issue");
        } else {
            return (ContainerFluentControl) fluentControlContainer.getFluentControl();
        }
    }

    private boolean isFluentControlAvailable() {
        return getControlContainer().getFluentControl() != null;
    }

    private void setFluentControl(ContainerFluentControl fluentControl) {
        getControlContainer().setFluentControl(fluentControl);
    }
}
