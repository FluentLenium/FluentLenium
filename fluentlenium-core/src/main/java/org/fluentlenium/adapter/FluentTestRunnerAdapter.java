package org.fluentlenium.adapter;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.fluentlenium.adapter.SharedMutator.EffectiveParameters;
import org.fluentlenium.adapter.exception.AnnotationNotFoundException;
import org.fluentlenium.adapter.exception.MethodNotFoundException;
import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriver;
import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FluentLenium Test Runner Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your Test class.
 */
@SuppressWarnings("PMD.GodClass")
public class FluentTestRunnerAdapter extends FluentAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FluentTestRunnerAdapter.class);

    private final SharedMutator sharedMutator;

    private static final ThreadLocal<EffectiveParameters<?>> PARAMETERS_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<String> TEST_METHOD_NAME = new ThreadLocal<>();
    private static final ThreadLocal<Class<?>> TEST_CLASS = new ThreadLocal<>();

    /**
     * Creates a new test runner adapter.
     */
    public FluentTestRunnerAdapter() {
        this(new DefaultFluentControlContainer());
    }

    /**
     * Creates a test runner adapter, with a custom driver container.
     *
     * @param driverContainer driver container
     */
    public FluentTestRunnerAdapter(FluentControlContainer driverContainer) {
        this(driverContainer, new DefaultSharedMutator());
    }

    /**
     * Creates a test runner adapter, with a custom shared mutator.
     *
     * @param sharedMutator shared mutator.
     */
    public FluentTestRunnerAdapter(SharedMutator sharedMutator) {
        this(new DefaultFluentControlContainer(), sharedMutator);
    }

    /**
     * Creates a test runner adapter, with a customer driver container and a customer shared mutator.
     *
     * @param driverContainer driver container
     * @param sharedMutator   shared mutator
     */
    public FluentTestRunnerAdapter(FluentControlContainer driverContainer, SharedMutator sharedMutator) {
        super(driverContainer);
        this.sharedMutator = sharedMutator;
    }

    /**
     * Creates a test runner adapter, with a customer driver container and a customer shared mutator.
     * It is possible to pass class from which the FluentConfiguration annotation will be loaded.
     *
     * @param driverContainer driver container
     * @param clazz           class from which FluentConfiguration annotation will be loaded
     * @param sharedMutator   shared mutator
     */
    public FluentTestRunnerAdapter(FluentControlContainer driverContainer, Class clazz, SharedMutator sharedMutator) {
        super(driverContainer, clazz);
        this.sharedMutator = sharedMutator;
    }


    /**
     * Invoked when a test class has finished (whatever the success of failing status)
     *
     * @param testClass test class to terminate
     */
    public static void afterClass(Class<?> testClass) {
        List<SharedWebDriver> sharedWebDrivers = SharedWebDriverContainer.INSTANCE.getTestClassDrivers(testClass);
        for (SharedWebDriver sharedWebDriver : sharedWebDrivers) {
            SharedWebDriverContainer.INSTANCE.quit(sharedWebDriver);
        }
    }

    /**
     * @return Class of currently running test
     */
    public Class<?> getTestClass() {
        Class<?> currentTestClass = FluentTestRunnerAdapter.TEST_CLASS.get();
        if (currentTestClass == null) {
            LOGGER.warn("Current test class is null. Are you in test context?");
        }
        return currentTestClass;
    }

    /**
     * @return method name (as String) of currently running test
     */
    public String getTestMethodName() {
        String currentTestMethodName = FluentTestRunnerAdapter.TEST_METHOD_NAME.get();
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
    public <T extends Annotation> T getClassAnnotation(Class<T> annotation) {
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
    public <T extends Annotation> T getMethodAnnotation(Class<T> annotation) {
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
     */
    protected void starting() {
        starting(getClass());
    }

    /**
     * Invoked when a test method is starting.
     *
     * @param testName Test name
     */
    protected void starting(String testName) {
        starting(getClass(), testName);
    }

    /**
     * Invoked when a test method is starting.
     *
     * @param testClass Test class
     */
    protected void starting(Class<?> testClass) {
        starting(testClass, testClass.getName());
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
            this.failed(testClass, testName);

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
     */
    protected void finished() {
        finished(getClass());
    }

    /**
     * Invoked when a test method has finished (whatever the success of failing status)
     *
     * @param testName Test name
     */
    protected void finished(String testName) {
        finished(getClass(), testName);
    }

    /**
     * Invoked when a test method has finished (whatever the success of failing status)
     *
     * @param testClass Test class
     */
    protected void finished(Class<?> testClass) {
        finished(testClass, testClass.getName());
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
     */
    protected void failed() {
        failed(getClass());
    }

    /**
     * Invoked when a test method has failed (before finished)
     *
     * @param testName Test name
     */
    protected void failed(String testName) {
        failed(getClass(), testName);
    }

    /**
     * Invoked when a test method has failed (before finished)
     *
     * @param testClass Test class
     */
    protected void failed(Class<?> testClass) {
        failed(testClass, testClass.getName());
    }

    /**
     * Invoked when a test method has failed (before finished)
     *
     * @param testClass Test class
     * @param testName  Test name
     */
    protected void failed(Class<?> testClass, String testName) {
        failed(null, testClass, testName);
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
}
