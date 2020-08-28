package org.fluentlenium.adapter.testng;

import org.fluentlenium.adapter.DefaultSharedMutator;
import org.fluentlenium.adapter.IFluentAdapter;
import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.adapter.SharedMutator.EffectiveParameters;
import org.fluentlenium.adapter.TestRunnerAdapter;
import org.fluentlenium.adapter.ThreadLocalFluentControlContainer;
import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriver;
import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import org.fluentlenium.core.inject.ContainerFluentControl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.fluentlenium.utils.ExceptionUtil.getCauseMessage;
import static org.fluentlenium.utils.ScreenshotUtil.isIgnoredException;
import static org.fluentlenium.utils.ThreadLocalAdapterUtil.clearThreadLocals;
import static org.fluentlenium.utils.ThreadLocalAdapterUtil.getClassAnnotationForClass;
import static org.fluentlenium.utils.ThreadLocalAdapterUtil.getClassFromThread;
import static org.fluentlenium.utils.ThreadLocalAdapterUtil.getMethodAnnotationForMethod;
import static org.fluentlenium.utils.ThreadLocalAdapterUtil.getMethodNameFromThread;
import static org.fluentlenium.utils.ThreadLocalAdapterUtil.setTestClassAndMethodValues;

/**
 * FluentLenium Test Runner Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your Test class.
 */
@SuppressWarnings("PMD.GodClass")
class SpringTestNGAdapter extends SpringTestNGControl implements TestRunnerAdapter, IFluentAdapter {

    private final SharedMutator sharedMutator;

    private static final ThreadLocal<EffectiveParameters<?>> PARAMETERS_THREAD_LOCAL = new ThreadLocal<>();
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
        return getClassFromThread(TEST_CLASS);
    }

    @Override
    public String getTestMethodName() {
        return getMethodNameFromThread(TEST_METHOD_NAME);
    }

    @Override
    public <T extends Annotation> T getClassAnnotation(Class<T> annotation) {
        return getClassAnnotationForClass(annotation, getClassFromThread(TEST_CLASS));
    }

    @Override
    public <T extends Annotation> T getMethodAnnotation(Class<T> annotation) {
        return getMethodAnnotationForMethod(
                annotation,
                getClassFromThread(TEST_CLASS),
                getMethodNameFromThread(TEST_METHOD_NAME));
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
            sharedWebDriver = SharedWebDriverContainer.INSTANCE.getSharedWebDriver(
                    PARAMETERS_THREAD_LOCAL.get(), null, this::newWebDriver, getConfiguration());
        } catch (ExecutionException | InterruptedException e) {
            this.failed(null, testClass, testName);

            String causeMessage = getCauseMessage(e);

            throw new WebDriverException("Browser failed to start, test [ " + testName + " ] execution interrupted."
                    + (isEmpty(causeMessage) ? "" : "\nCaused by: [ " + causeMessage + "]"), e);
        }

        setTestClassAndMethodValues(PARAMETERS_THREAD_LOCAL, TEST_CLASS, TEST_METHOD_NAME);
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
        EffectiveParameters<?> parameters = sharedMutator.getEffectiveParameters(testClass, testName,
                driverLifecycle);
        SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE.getDriver(parameters);

        if (driverLifecycle == DriverLifecycle.METHOD || driverLifecycle == DriverLifecycle.THREAD) {
            Optional.ofNullable(sharedWebDriver).ifPresent(SharedWebDriverContainer.INSTANCE::quit);
        }

        if (getDeleteCookies()) {
            Optional.ofNullable(sharedWebDriver).ifPresent(shared -> shared.getDriver().manage().deleteAllCookies());
        }

        clearThreadLocals(PARAMETERS_THREAD_LOCAL, TEST_CLASS, TEST_METHOD_NAME);
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
            } catch (Exception ignored) {
            }

            try {
                if (getHtmlDumpMode() == TriggerMode.AUTOMATIC_ON_FAIL && getDriver() != null) {
                    takeHtmlDump(testClass.getSimpleName() + "_" + testName + ".html");
                }
            } catch (Exception ignored) {
            }

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
