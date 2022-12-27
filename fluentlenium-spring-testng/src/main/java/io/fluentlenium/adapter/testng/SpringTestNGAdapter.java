package io.fluentlenium.adapter.testng;

import io.fluentlenium.adapter.*;import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriver;import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;import io.fluentlenium.core.inject.ContainerFluentControl;import io.fluentlenium.utils.AnnotationUtil;import io.fluentlenium.utils.ScreenshotUtil;import io.fluentlenium.utils.ThreadLocalAdapterUtil;import io.fluentlenium.adapter.DefaultSharedMutator;
import io.fluentlenium.adapter.IFluentAdapter;
import io.fluentlenium.adapter.SharedMutator;
import io.fluentlenium.adapter.SharedMutator.EffectiveParameters;
import io.fluentlenium.adapter.TestRunnerAdapter;
import io.fluentlenium.adapter.ThreadLocalFluentControlContainer;
import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriver;
import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import io.fluentlenium.core.inject.ContainerFluentControl;
import org.openqa.selenium.WebDriver;

import java.lang.annotation.Annotation;

import static io.fluentlenium.adapter.TestRunnerCommon.deleteCookies;
import static io.fluentlenium.adapter.TestRunnerCommon.doHtmlDump;
import static io.fluentlenium.adapter.TestRunnerCommon.doScreenshot;
import static io.fluentlenium.adapter.TestRunnerCommon.getTestDriver;
import static io.fluentlenium.adapter.TestRunnerCommon.quitMethodAndThreadDrivers;
import static io.fluentlenium.utils.AnnotationUtil.getClassAnnotationForClass;
import static io.fluentlenium.utils.AnnotationUtil.getMethodAnnotationForMethod;
import static io.fluentlenium.utils.ScreenshotUtil.isIgnoredException;
import static io.fluentlenium.utils.ThreadLocalAdapterUtil.clearThreadLocals;
import static io.fluentlenium.utils.ThreadLocalAdapterUtil.getClassFromThread;
import static io.fluentlenium.utils.ThreadLocalAdapterUtil.getMethodNameFromThread;
import static io.fluentlenium.utils.ThreadLocalAdapterUtil.setTestClassAndMethodValues;

/**
 * FluentLenium Test Runner Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your Test class.
 */
@SuppressWarnings("PMD.GodClass")
class SpringTestNGAdapter extends SpringTestNGControl implements TestRunnerAdapter, IFluentAdapter {

    private final SharedMutator sharedMutator;

    private static final ThreadLocal<SharedMutator.EffectiveParameters<?>> PARAMETERS_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<String> TEST_METHOD_NAME = new ThreadLocal<>();
    private static final ThreadLocal<Class<?>> TEST_CLASS = new ThreadLocal<>();

    /**
     * Creates a new test runner adapter.
     */
    SpringTestNGAdapter() {
        super(new ThreadLocalFluentControlContainer());
        this.sharedMutator = new DefaultSharedMutator();
    }

    @Override
    public Class<?> getTestClass() {
        return ThreadLocalAdapterUtil.getClassFromThread(TEST_CLASS);
    }

    @Override
    public String getTestMethodName() {
        return ThreadLocalAdapterUtil.getMethodNameFromThread(TEST_METHOD_NAME);
    }

    @Override
    public <T extends Annotation> T getClassAnnotation(Class<T> annotation) {
        return AnnotationUtil.getClassAnnotationForClass(annotation, ThreadLocalAdapterUtil.getClassFromThread(TEST_CLASS));
    }

    @Override
    public <T extends Annotation> T getMethodAnnotation(Class<T> annotation) {
        return AnnotationUtil.getMethodAnnotationForMethod(
                annotation,
                ThreadLocalAdapterUtil.getClassFromThread(TEST_CLASS),
                ThreadLocalAdapterUtil.getMethodNameFromThread(TEST_METHOD_NAME));
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

        SharedWebDriver sharedWebDriver = TestRunnerCommon.getTestDriver(testClass, testName,
                this::newWebDriver, this::failed,
                getConfiguration(), PARAMETERS_THREAD_LOCAL.get());
        ThreadLocalAdapterUtil.setTestClassAndMethodValues(PARAMETERS_THREAD_LOCAL, TEST_CLASS, TEST_METHOD_NAME);
        initFluent(sharedWebDriver.getDriver());
    }

    /**
     * Invoked when a test method has finished (whatever the success of failing status)
     *
     * @param testClass Test class
     * @param testName  Test name
     */
    protected void finished(Class<?> testClass, String testName) {
        DriverLifecycle driverLifecycle = getDriverLifecycle();
        SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE
                .getDriver(sharedMutator.getEffectiveParameters(testClass, testName, driverLifecycle));

        TestRunnerCommon.quitMethodAndThreadDrivers(driverLifecycle, sharedWebDriver);
        TestRunnerCommon.deleteCookies(sharedWebDriver, getConfiguration());
        ThreadLocalAdapterUtil.clearThreadLocals(PARAMETERS_THREAD_LOCAL, TEST_CLASS, TEST_METHOD_NAME);
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
        if (isFluentControlAvailable() && !ScreenshotUtil.isIgnoredException(e)) {
            TestRunnerCommon.doScreenshot(testClass, testName, this, getConfiguration());
            TestRunnerCommon.doHtmlDump(testClass, testName, this, getConfiguration());
        }
    }

    @Override
    public final WebDriver getDriver() {
        return IFluentAdapter.super.getDriver();
    }

    @Override
    public ContainerFluentControl getFluentControl() {
        return IFluentAdapter.super.getFluentControl();
    }

}
