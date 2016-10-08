package org.fluentlenium.adapter;

import com.google.common.base.Supplier;
import org.fluentlenium.adapter.SharedMutator.EffectiveParameters;
import org.openqa.selenium.WebDriver;

import java.util.List;

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
    public FluentTestRunnerAdapter(final FluentControlContainer driverContainer) {
        this(driverContainer, new DefaultSharedMutator());
    }

    /**
     * Creates a test runner adapter, with a custom shared mutator.
     *
     * @param sharedMutator shared mutator.
     */
    public FluentTestRunnerAdapter(final SharedMutator sharedMutator) {
        this(new DefaultFluentControlContainer(), sharedMutator);
    }

    /**
     * Creates a test runner adapter, with a customer driver container and a customer shared mutator.
     *
     * @param driverContainer driver container
     * @param sharedMutator   shared mutator
     */
    public FluentTestRunnerAdapter(final FluentControlContainer driverContainer, final SharedMutator sharedMutator) {
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
    protected void starting(final String testName) {
        starting(getClass(), testName);
    }

    /**
     * Invoked when a test method is starting.
     *
     * @param testClass Test class
     */
    protected void starting(final Class<?> testClass) {
        starting(testClass, testClass.getName());
    }

    /**
     * Invoked when a test method is starting.
     *
     * @param testClass Test class
     * @param testName  Test name
     */
    protected void starting(final Class<?> testClass, final String testName) {
        final EffectiveParameters<?> parameters = this.sharedMutator
                .getEffectiveParameters(testClass, testName, getDriverLifecycle());

        final SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE.getOrCreateDriver(new Supplier<WebDriver>() {
            @Override
            public WebDriver get() {
                return FluentTestRunnerAdapter.this.newWebDriver();
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
    protected void finished(final String testName) {
        finished(getClass(), testName);
    }

    /**
     * Invoked when a test method has finished (whatever the success of failing status)
     *
     * @param testClass Test class
     */
    protected void finished(final Class<?> testClass) {
        finished(testClass, testClass.getName());
    }

    /**
     * Invoked when a test method has finished (whatever the success of failing status)
     *
     * @param testClass Test class
     * @param testName  Test name
     */
    protected void finished(final Class<?> testClass, final String testName) {
        final DriverLifecycle driverLifecycle = getDriverLifecycle();

        if (driverLifecycle == DriverLifecycle.METHOD) {
            final EffectiveParameters<?> parameters = this.sharedMutator
                    .getEffectiveParameters(testClass, testName, driverLifecycle);

            final SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE
                    .getDriver(parameters.getTestClass(), parameters.getTestName(), parameters.getDriverLifecycle());

            if (sharedWebDriver != null) {
                SharedWebDriverContainer.INSTANCE.quit(sharedWebDriver);
            }
        } else if (getDeleteCookies() != null && getDeleteCookies()) {
            final EffectiveParameters<?> sharedParameters = this.sharedMutator
                    .getEffectiveParameters(testClass, testName, driverLifecycle);

            final SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE
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
    public static void afterClass(final Class<?> testClass) {
        final List<SharedWebDriver> sharedWebDrivers = SharedWebDriverContainer.INSTANCE.getTestClassDrivers(testClass);
        for (final SharedWebDriver sharedWebDriver : sharedWebDrivers) {
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
    protected void failed(final String testName) {
        failed(getClass(), testName);
    }

    /**
     * Invoked when a test method has failed (before finished)
     *
     * @param testClass Test class
     */
    protected void failed(final Class<?> testClass) {
        failed(testClass, testClass.getName());
    }

    /**
     * Invoked when a test method has failed (before finished)
     *
     * @param testClass Test class
     * @param testName  Test name
     */
    protected void failed(final Class<?> testClass, final String testName) {
        failed(null, testClass, testName);
    }

    /**
     * Invoked when a test method has failed (before finished)
     *
     * @param e         Throwable thrown by the failing test.
     * @param testClass Test class
     * @param testName  Test name
     */
    protected void failed(final Throwable e, final Class<?> testClass, final String testName) {
        if (isFluentControlAvailable()) {
            try {
                if (getScreenshotMode() == TriggerMode.AUTOMATIC_ON_FAIL && canTakeScreenShot()) {
                    takeScreenShot(testClass.getSimpleName() + "_" + testName + ".png");
                }
            } catch (final Exception exception) { // NOPMD EmptyCatchBlock
                // Can't write screenshot, for some reason.
            }

            try {
                if (getHtmlDumpMode() == TriggerMode.AUTOMATIC_ON_FAIL && getDriver() != null) {
                    takeHtmlDump(testClass.getSimpleName() + "_" + testName + ".html");
                }
            } catch (final Exception exception) { // NOPMD EmptyCatchBlock
                // Can't write htmldump, for some reason.
            }

        }
    }
}
