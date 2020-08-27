package org.fluentlenium.adapter.spock

import org.apache.commons.lang3.StringUtils
import org.fluentlenium.adapter.DefaultSharedMutator
import org.fluentlenium.adapter.FluentControlContainer
import org.fluentlenium.adapter.SharedMutator
import org.fluentlenium.adapter.TestRunnerAdapter
import org.fluentlenium.adapter.exception.AnnotationNotFoundException
import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriver
import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer
import org.fluentlenium.configuration.WebDrivers
import org.fluentlenium.core.FluentDriver
import org.fluentlenium.core.inject.ContainerContext
import org.fluentlenium.core.inject.ContainerFluentControl
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.support.events.EventFiringWebDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.lang.annotation.Annotation
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

// Intellij is wrong here - do not delete
import static org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle
import static org.apache.commons.lang3.StringUtils.isEmpty
import static org.fluentlenium.utils.ExceptionUtil.getCauseMessage
import static org.fluentlenium.utils.ExecutorServiceUtil.getExecutor
import static org.fluentlenium.utils.ExecutorServiceUtil.shutDownExecutor
import static org.fluentlenium.utils.ScreenshotUtil.isIgnoredException;

class SpockAdapter extends SpockControl implements TestRunnerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpockAdapter.class)

    private SharedMutator sharedMutator = new DefaultSharedMutator()

    private static final ThreadLocal<SharedMutator.EffectiveParameters<?>> PARAMETERS_THREAD_LOCAL = new ThreadLocal<>()
    private static final ThreadLocal<String> TEST_METHOD_NAME = new ThreadLocal<>()
    private static final ThreadLocal<Class<?>> TEST_CLASS = new ThreadLocal<>()

    @Override
    Class<?> getTestClass() {
        Class<?> currentTestClass = TEST_CLASS.get()
        if (currentTestClass == null) {
            LOGGER.warn("Current test class is null. Are you in test context?")
        }
        return currentTestClass
    }

    @Override
    String getTestMethodName() {
        String currentTestMethodName = TEST_METHOD_NAME.get()
        if (currentTestMethodName == null) {
            LOGGER.warn("Current test method name is null. Are you in text context?")
        }
        return currentTestMethodName
    }

    @Override
    <T extends Annotation> T getClassAnnotation(Class<T> annotation) {
        def anno = specificationContext.currentSpec.getAnnotation(annotation)

        if (anno == null) {
            throw new AnnotationNotFoundException();
        }

        return anno
    }

    @Override
    <T extends Annotation> T getMethodAnnotation(Class<T> annotation) {
        def anno = specificationContext.currentFeature.featureMethod.getAnnotation(annotation)

        if (anno == null) {
            throw new AnnotationNotFoundException();
        }

        return anno
    }

    /**
     * Invoked when a test method is starting.
     *
     * @param testClass Test class
     * @param testName Test name
     */
    void starting(Class<?> testClass, String testName) {
        PARAMETERS_THREAD_LOCAL.set(sharedMutator.getEffectiveParameters(testClass, testName, getDriverLifecycle()))

        SharedWebDriver sharedWebDriver

        try {
            sharedWebDriver = getSharedWebDriver(PARAMETERS_THREAD_LOCAL.get())
        } catch (ExecutionException | InterruptedException e) {
            this.failed(null, testClass, testName)

            String causeMessage = getCauseMessage(e)

            throw new WebDriverException("Browser failed to start, test [ " + testName + " ] execution interrupted."
                    + (isEmpty(causeMessage) ? "" : "\nCaused by: [ " + causeMessage + "]"), e)
        }

        initFluent(sharedWebDriver.getDriver())
    }

    @SuppressWarnings("UnnecessaryQualifiedReference")
    private static void setTestClassAndMethodValues() {
        Optional.ofNullable(PARAMETERS_THREAD_LOCAL.get()).ifPresent((effectiveParameters) -> {
            Optional.ofNullable(effectiveParameters.getTestClass()).ifPresent(SpockAdapter.TEST_CLASS::set)
            Optional.ofNullable(effectiveParameters.getTestName()).ifPresent(SpockAdapter::setMethodName)
        })
    }

    private static void setMethodName(String methodName) {
        String className = StringUtils.substringBefore(methodName, "(")
        TEST_METHOD_NAME.set(className)
    }

    SharedWebDriver getSharedWebDriver(SharedMutator.EffectiveParameters<?> parameters)
            throws ExecutionException, InterruptedException {
        return getSharedWebDriver(parameters, null)
    }

    /**
     * Returns SharedDriver instance
     *
     * @param parameters driver parameters
     * @param webDriverExecutor executor service
     * @return SharedDriver* @throws ExecutionException   execution exception
     * @throws InterruptedException interrupted exception
     */
    protected SharedWebDriver getSharedWebDriver(SharedMutator.EffectiveParameters<?> parameters,
                                                 ExecutorService webDriverExecutor)
            throws ExecutionException, InterruptedException {
        SharedWebDriver sharedWebDriver = null
        ExecutorService executorService = getExecutor(webDriverExecutor)

        for (int retryCount = 0; retryCount < getBrowserTimeoutRetries(); retryCount++) {

            def futureWebDriver = createDriver(parameters, executorService)
            shutDownExecutor(executorService, getBrowserTimeout())

            try {
                sharedWebDriver = futureWebDriver.get()
            } catch (InterruptedException | ExecutionException e) {
                executorService.shutdownNow()
                throw e
            }

            if (sharedWebDriver != null) {
                break
            }
        }

        setTestClassAndMethodValues()
        return sharedWebDriver
    }

    private Future<SharedWebDriver> createDriver(SharedMutator.EffectiveParameters<?> parameters, ExecutorService executorService) {
        return executorService.submit({ ->
            SharedWebDriverContainer.INSTANCE.getOrCreateDriver(this::newWebDriver, parameters)
        } as Callable)
    }

    private static void clearThreadLocals() {
        PARAMETERS_THREAD_LOCAL.remove()
        TEST_CLASS.remove()
        TEST_METHOD_NAME.remove()
    }

    /**
     * Invoked when a test method has finished (whatever the success of failing status)
     *
     * @param testClass Test class
     * @param testName Test name
     */
    protected void finished(Class<?> testClass, String testName) {
        DriverLifecycle driverLifecycle = getDriverLifecycle()
        SharedMutator.EffectiveParameters<?> parameters = sharedMutator.getEffectiveParameters(testClass, testName, driverLifecycle)
        SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE.getDriver(parameters)

        if (driverLifecycle == DriverLifecycle.METHOD || driverLifecycle == DriverLifecycle.THREAD) {
            Optional.ofNullable(sharedWebDriver).ifPresent(SharedWebDriverContainer.INSTANCE::quit)
        }

        if (getDeleteCookies()) {
            Optional.ofNullable(sharedWebDriver).ifPresent(shared -> shared.getDriver().manage().deleteAllCookies())
        }

        clearThreadLocals()
        releaseFluent()
    }

    /**
     * Invoked when a test method has failed (before finished)
     *
     * @param e Throwable thrown by the failing test.
     * @param testClass Test class
     * @param testName Test name
     */
    protected void failed(Throwable e, Class<?> testClass, String testName) {
        if (isFluentControlAvailable() && !isIgnoredException(e)) {
            try {
                if (getScreenshotMode() == TriggerMode.AUTOMATIC_ON_FAIL && canTakeScreenShot()) {
                    this.takeScreenshot(testClass.getSimpleName() + "_" + testName + ".png")
                }
            } catch (Exception ignored) {
            }

            try {
                if (getHtmlDumpMode() == TriggerMode.AUTOMATIC_ON_FAIL && getDriver() != null) {
                    takeHtmlDump(testClass.getSimpleName() + "_" + testName + ".html")
                }
            } catch (Exception ignored) {
            }

        }
    }

    @Override
    final WebDriver getDriver() {
        return getFluentControl() == null ? null : getFluentControl().getDriver()
    }

    /**
     * Load a {@link WebDriver} into this adapter.
     * <p>
     * This method should not be called by end user.
     *
     * @param webDriver webDriver to use.
     * @throws IllegalStateException when trying to register a different webDriver that the current one.
     */
    void initFluent(WebDriver webDriver) {
        if (webDriver == null) {
            releaseFluent()
            return
        }

        if (getFluentControl() != null) {
            if (getFluentControl().getDriver() == webDriver) {
                return
            }
            if (getFluentControl().getDriver() != null) {
                throw new IllegalStateException("Trying to init a WebDriver, but another one is still running")
            }
        }

        ContainerFluentControl adapterFluentControl = new ContainerFluentControl(new FluentDriver(webDriver, this, this))
        setFluentControl(adapterFluentControl)
        ContainerContext context = adapterFluentControl.inject(this)
        adapterFluentControl.setContext(context)
    }

    /**
     * Release the current {@link WebDriver} from this adapter.
     * <p>
     * This method should not be called by end user.
     */
    void releaseFluent() {
        if (getFluentControl() != null) {
            ((FluentDriver) getFluentControl().getAdapterControl()).releaseFluent()
            setFluentControl(null)
        }
    }

    /**
     * Creates a new {@link WebDriver} instance.
     * <p>
     * This method should not be called by end user, but may be overriden if required.
     * <p>
     * Before overriding this method, you should consider using {@link org.fluentlenium.configuration.WebDrivers} registry and configuration
     * {@link org.fluentlenium.configuration.ConfigurationProperties#getWebDriver()}.
     * <p>
     * To retrieve the current managed {@link WebDriver}, call {@link #getDriver()} instead.
     *
     * @return A new WebDriver instance.
     * @see #getDriver()
     */
    WebDriver newWebDriver() {
        WebDriver webDriver = WebDrivers.INSTANCE.newWebDriver(getWebDriver(), getCapabilities(), this)
        if (Boolean.TRUE == getEventsEnabled()) {
            webDriver = new EventFiringWebDriver(webDriver)
        }
        return webDriver
    }

    ContainerFluentControl getFluentControl() {
        FluentControlContainer fluentControlContainer = getControlContainer()

        if (fluentControlContainer == null) {
            throw new IllegalStateException("FluentControl is not initialized, WebDriver or Configuration issue")
        } else {
            return (ContainerFluentControl) fluentControlContainer.getFluentControl()
        }
    }

    private boolean isFluentControlAvailable() {
        return getControlContainer().getFluentControl() != null
    }

    private void setFluentControl(ContainerFluentControl fluentControl) {
        getControlContainer().setFluentControl(fluentControl)
    }


}
