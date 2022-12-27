package io.fluentlenium.adapter.spock

import org.openqa.selenium.WebDriver

import java.lang.annotation.Annotation

import static io.fluentlenium.adapter.TestRunnerCommon.*
import static io.fluentlenium.utils.ScreenshotUtil.isIgnoredException

// Intellij is wrong here - do not delete

import static io.fluentlenium.utils.ThreadLocalAdapterUtil.*

class SpockAdapter extends SpockControl implements io.fluentlenium.adapter.TestRunnerAdapter, io.fluentlenium.adapter.IFluentAdapter {

    private io.fluentlenium.adapter.SharedMutator sharedMutator = new io.fluentlenium.adapter.DefaultSharedMutator()

    private static final ThreadLocal<io.fluentlenium.adapter.SharedMutator.EffectiveParameters<?>> PARAMETERS_THREAD_LOCAL = new ThreadLocal<>()
    private static final ThreadLocal<String> TEST_METHOD_NAME = new ThreadLocal<>()
    private static final ThreadLocal<Class<?>> TEST_CLASS = new ThreadLocal<>()

    @Override
    Class<?> getTestClass() {
        return getClassFromThread(TEST_CLASS)
    }

    @Override
    String getTestMethodName() {
        return getMethodNameFromThread(TEST_METHOD_NAME)
    }

    @Override
    <T extends Annotation> T getClassAnnotation(Class<T> annotation) {
        def anno = specificationContext.currentSpec.getAnnotation(annotation)

        if (anno == null) {
            throw new io.fluentlenium.adapter.exception.AnnotationNotFoundException()
        }

        return anno
    }

    @Override
    <T extends Annotation> T getMethodAnnotation(Class<T> annotation) {
        def anno = specificationContext.currentFeature.featureMethod.getAnnotation(annotation)

        if (anno == null) {
            throw new io.fluentlenium.adapter.exception.AnnotationNotFoundException()
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
        PARAMETERS_THREAD_LOCAL.set(sharedMutator.getEffectiveParameters(testClass, testName,
                getDriverLifecycle()))

        io.fluentlenium.adapter.sharedwebdriver.SharedWebDriver sharedWebDriver = getTestDriver(testClass, testName,
                this::newWebDriver, this::failed,
                getConfiguration(), PARAMETERS_THREAD_LOCAL.get())
        setTestClassAndMethodValues(PARAMETERS_THREAD_LOCAL, TEST_CLASS, TEST_METHOD_NAME)
        initFluent(sharedWebDriver.getDriver())
    }

    /**
     * Invoked when a test method has finished (whatever the success of failing status)
     *
     * @param testClass Test class
     * @param testName Test name
     */
    protected void finished(Class<?> testClass, String testName) {
        DriverLifecycle driverLifecycle = getDriverLifecycle()
        io.fluentlenium.adapter.sharedwebdriver.SharedWebDriver sharedWebDriver = io.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer.INSTANCE
                .getDriver(sharedMutator.getEffectiveParameters(testClass, testName, driverLifecycle))

        quitMethodAndThreadDrivers(driverLifecycle, sharedWebDriver)
        deleteCookies(sharedWebDriver, getConfiguration())
        clearThreadLocals(PARAMETERS_THREAD_LOCAL, TEST_CLASS, TEST_METHOD_NAME)
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
            doScreenshot(testClass, testName, this, getConfiguration())
            doHtmlDump(testClass, testName, this, getConfiguration())
        }
    }

    @Override
    final WebDriver getDriver() {
        try {
            return Optional.ofNullable(getFluentControl().getDriver())
                    .orElse(null)
        } catch (NullPointerException ignored) {
            return null
        }
    }

}
