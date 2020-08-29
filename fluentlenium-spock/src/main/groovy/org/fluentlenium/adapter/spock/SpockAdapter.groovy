package org.fluentlenium.adapter.spock

import org.fluentlenium.adapter.DefaultSharedMutator
import org.fluentlenium.adapter.IFluentAdapter
import org.fluentlenium.adapter.SharedMutator
import org.fluentlenium.adapter.TestRunnerAdapter
import org.fluentlenium.adapter.exception.AnnotationNotFoundException
import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriver
import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer
import org.openqa.selenium.WebDriver

import java.lang.annotation.Annotation

import static org.fluentlenium.adapter.TestRunnerCommon.deleteCookies
import static org.fluentlenium.adapter.TestRunnerCommon.doHtmlDump
import static org.fluentlenium.adapter.TestRunnerCommon.doScreenshot
import static org.fluentlenium.adapter.TestRunnerCommon.getTestDriver
import static org.fluentlenium.adapter.TestRunnerCommon.quitMethodAndThreadDrivers

// Intellij is wrong here - do not delete
import static org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle
import static org.fluentlenium.utils.ScreenshotUtil.isIgnoredException
import static org.fluentlenium.utils.ThreadLocalAdapterUtil.clearThreadLocals
import static org.fluentlenium.utils.ThreadLocalAdapterUtil.getClassFromThread
import static org.fluentlenium.utils.ThreadLocalAdapterUtil.getMethodNameFromThread
import static org.fluentlenium.utils.ThreadLocalAdapterUtil.setTestClassAndMethodValues

class SpockAdapter extends SpockControl implements TestRunnerAdapter, IFluentAdapter {

    private SharedMutator sharedMutator = new DefaultSharedMutator()

    private static final ThreadLocal<SharedMutator.EffectiveParameters<?>> PARAMETERS_THREAD_LOCAL = new ThreadLocal<>()
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
            throw new AnnotationNotFoundException()
        }

        return anno
    }

    @Override
    <T extends Annotation> T getMethodAnnotation(Class<T> annotation) {
        def anno = specificationContext.currentFeature.featureMethod.getAnnotation(annotation)

        if (anno == null) {
            throw new AnnotationNotFoundException()
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

        SharedWebDriver sharedWebDriver = getTestDriver(testClass, testName,
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
        SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE
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
