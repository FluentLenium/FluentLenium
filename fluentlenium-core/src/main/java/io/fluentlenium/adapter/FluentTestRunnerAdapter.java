package io.fluentlenium.adapter;

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

import java.lang.annotation.Annotation;
import java.util.List;

import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriver;import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;import io.fluentlenium.utils.AnnotationUtil;import io.fluentlenium.utils.ThreadLocalAdapterUtil;import io.fluentlenium.adapter.SharedMutator.EffectiveParameters;
import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriver;
import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;

/**
 * FluentLenium Test Runner Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your Test class.
 */
@SuppressWarnings("PMD.GodClass")
public class FluentTestRunnerAdapter extends FluentAdapter implements TestRunnerAdapter {

    private final SharedMutator sharedMutator;

    private static final ThreadLocal<SharedMutator.EffectiveParameters<?>> PARAMETERS_THREAD_LOCAL = new ThreadLocal<>();
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
    public FluentTestRunnerAdapter(FluentControlContainer driverContainer, Class<?> clazz, SharedMutator sharedMutator) {
        super(driverContainer, clazz);
        this.sharedMutator = sharedMutator;
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

        SharedWebDriver sharedWebDriver = TestRunnerCommon.getTestDriver(testClass, testName,
                this::newWebDriver, this::failed,
                getConfiguration(), PARAMETERS_THREAD_LOCAL.get());
        ThreadLocalAdapterUtil.setTestClassAndMethodValues(PARAMETERS_THREAD_LOCAL, TEST_CLASS, TEST_METHOD_NAME);
        initFluent(sharedWebDriver.getDriver());
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
        SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE
                .getDriver(sharedMutator.getEffectiveParameters(testClass, testName, driverLifecycle));

        TestRunnerCommon.quitMethodAndThreadDrivers(driverLifecycle, sharedWebDriver);
        TestRunnerCommon.deleteCookies(sharedWebDriver, getConfiguration());
        ThreadLocalAdapterUtil.clearThreadLocals(PARAMETERS_THREAD_LOCAL, TEST_CLASS, TEST_METHOD_NAME);
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
            TestRunnerCommon.doScreenshot(testClass, testName, this, getConfiguration());
            TestRunnerCommon.doHtmlDump(testClass, testName, this, getConfiguration());
        }
    }

    /**
     * Invoked when a test class has finished (whatever the success of failing status)
     *
     * @param testClass test class to terminate
     */
    public static void classDriverCleanup(Class<?> testClass) {
        List<SharedWebDriver> sharedWebDrivers = SharedWebDriverContainer.INSTANCE.getTestClassDrivers(testClass);
        sharedWebDrivers.forEach(SharedWebDriverContainer.INSTANCE::quit);
    }
}
