package org.fluentlenium.adapter;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.lang.annotation.Annotation;
import java.util.List;
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

/**
 * FluentLenium Test Runner Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your Test class.
 */
public class FluentTestRunnerAdapter extends FluentAdapter {

    private SharedMutator sharedMutator;

    private static final ThreadLocal<EffectiveParameters<?>> parametersThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> testMethodName = new ThreadLocal<>();
    private static final ThreadLocal<Class<?>> testClass = new ThreadLocal<>();

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
     *
     * @return Class of currently running test
     */
    protected Class<?> getTestClass() {
        return testClass.get();
    }

    /**
     *
     * @return method name (as String) of currently running test
     */
    protected String getTestMethodName() {
        return testMethodName.get();
    }

    /**
     * Allows to access Class level annotation of currently running test
     *
     * @param annotation @interface you want to access
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
     * @param annotation @interface you want to access
     * @return Annotation instance
     * @throws AnnotationNotFoundException of annotation you want to access couldn't be found
     * @throws MethodNotFoundException if test method couldn't be found - if it occurs that's most likely FL bug
     *
     */
    protected <T extends Annotation> T getMethodAnnotation(Class<T> annotation) {
        T definedAnnotation;
        try {
            definedAnnotation = getTestClass().getDeclaredMethod(getTestMethodName()).getAnnotation(annotation);
        } catch (NoSuchMethodException e) {
            throw new MethodNotFoundException();
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
        parametersThreadLocal.set(sharedMutator.getEffectiveParameters(testClass, testName,
                getDriverLifecycle()));

        SharedWebDriver sharedWebDriver;

        try {
            sharedWebDriver = getSharedWebDriver(parametersThreadLocal.get());
        } catch (ExecutionException | InterruptedException e) {
            this.failed(testClass, testName);

            String causeMessage = this.getCauseMessage(e);

            throw new WebDriverException("Browser failed to start, test [ " + testName + " ] execution interrupted."
                    + (isEmpty(causeMessage) ? "" : "\nCaused by: [ " + causeMessage + "]"), e);
        }

        initFluent(sharedWebDriver.getDriver());
    }

    private void setTestClassAndMethodValues() {
        if (parametersThreadLocal.get() != null) {
            if (parametersThreadLocal.get().getTestClass() != null) {
                setClass();
            }
            if (parametersThreadLocal.get().getTestClass() != null) {
                setMethodName();
            }
        }
    }

    private void setMethodName() {
        String localTestName = parametersThreadLocal.get().getTestName();
        String className = StringUtils.substringBefore(localTestName, "(");
        testMethodName.set(className);
    }

    private void setClass() {
        Class<?> localTestClass = parametersThreadLocal.get().getTestClass();
        testClass.set(localTestClass);
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
    protected SharedWebDriver getSharedWebDriver(EffectiveParameters<?> parameters, ExecutorService webDriverExecutor)
            throws ExecutionException, InterruptedException {
        SharedWebDriver sharedWebDriver = null;
        ExecutorService setExecutorService = null;

        if (webDriverExecutor != null) {
            setExecutorService = webDriverExecutor;
        }

        for (int browserTimeoutRetryNo = 0; browserTimeoutRetryNo < getBrowserTimeoutRetries()
                && sharedWebDriver == null; browserTimeoutRetryNo++) {
            if (setExecutorService == null) {
                webDriverExecutor = Executors.newSingleThreadExecutor();
            } else {
                webDriverExecutor = setExecutorService;
            }

            Future<SharedWebDriver> futureWebDriver = webDriverExecutor.submit
                    (() -> SharedWebDriverContainer.INSTANCE
                            .getOrCreateDriver(this::newWebDriver, parameters.getTestClass(),
                                    parameters.getTestName(), parameters.getDriverLifecycle()));

            setTestClassAndMethodValues();

            webDriverExecutor.shutdown();
            try {
                if (!webDriverExecutor.awaitTermination(getBrowserTimeout(), TimeUnit.MILLISECONDS)) {
                    webDriverExecutor.shutdownNow();
                }

                sharedWebDriver = futureWebDriver.get();
            } catch (InterruptedException | ExecutionException e) {
                webDriverExecutor.shutdownNow();
                throw e;
            }
        }

        return sharedWebDriver;
    }

    private void clearThreadLocals() {
        parametersThreadLocal.remove();
        testClass.remove();
        testMethodName.remove();
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

        if (driverLifecycle == DriverLifecycle.METHOD || driverLifecycle == DriverLifecycle.THREAD) {
            EffectiveParameters<?> parameters = sharedMutator.getEffectiveParameters(testClass, testName,
                    driverLifecycle);

            SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE
                    .getDriver(parameters.getTestClass(), parameters.getTestName(), parameters.getDriverLifecycle());

            if (sharedWebDriver != null) {
                SharedWebDriverContainer.INSTANCE.quit(sharedWebDriver);
            }
        } else if (getDeleteCookies() != null && getDeleteCookies()) {
            EffectiveParameters<?> sharedParameters = sharedMutator.getEffectiveParameters(testClass, testName,
                    driverLifecycle);

            SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE
                    .getDriver(sharedParameters.getTestClass(), sharedParameters.getTestName(),
                            sharedParameters.getDriverLifecycle());

            if (sharedWebDriver != null) {
                sharedWebDriver.getDriver().manage().deleteAllCookies();
            }
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
