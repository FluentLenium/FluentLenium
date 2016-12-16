package org.fluentlenium.adapter;

import org.fluentlenium.adapter.SharedMutator.EffectiveParameters;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.function.Supplier;

/**
 * FluentLenium Test Runner Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your Test class.
 */
public class FluentTestRunnerAdapter extends FluentAdapter {
    private final SharedMutator sharedMutator;

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
        EffectiveParameters<?> parameters = sharedMutator.getEffectiveParameters(testClass, testName, getDriverLifecycle());

        SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE.getOrCreateDriver(new Supplier<WebDriver>() {
            @Override
            public WebDriver get() {
                return newWebDriver();
            }
        }, parameters.getTestClass(), parameters.getTestName(), parameters.getDriverLifecycle());

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

        if (driverLifecycle == DriverLifecycle.METHOD) {
            EffectiveParameters<?> parameters = sharedMutator.getEffectiveParameters(testClass, testName, driverLifecycle);

            SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE
                    .getDriver(parameters.getTestClass(), parameters.getTestName(), parameters.getDriverLifecycle());

            if (sharedWebDriver != null) {
                SharedWebDriverContainer.INSTANCE.quit(sharedWebDriver);
            }
        } else if (getDeleteCookies() != null && getDeleteCookies()) {
            EffectiveParameters<?> sharedParameters = sharedMutator.getEffectiveParameters(testClass, testName, driverLifecycle);

            SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE
                    .getDriver(sharedParameters.getTestClass(), sharedParameters.getTestName(),
                            sharedParameters.getDriverLifecycle());

            if (sharedWebDriver != null) {
                sharedWebDriver.getDriver().manage().deleteAllCookies();
            }
        }

        releaseFluent();

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
        if (isFluentControlAvailable()) {
            try {
                if (getScreenshotMode() == TriggerMode.AUTOMATIC_ON_FAIL && canTakeScreenShot()) {
                    takeScreenShot(testClass.getSimpleName() + "_" + testName + ".png");
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
