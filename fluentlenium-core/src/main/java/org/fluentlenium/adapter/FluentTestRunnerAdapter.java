package org.fluentlenium.adapter;

import com.google.common.base.Supplier;
import org.fluentlenium.adapter.util.CookieStrategyReader;
import org.fluentlenium.adapter.util.DefaultCookieStrategyReader;
import org.fluentlenium.adapter.util.DefaultSharedDriverStrategyReader;
import org.fluentlenium.adapter.util.SharedDriverStrategy;
import org.fluentlenium.adapter.util.SharedDriverStrategyReader;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Generic Test Runner Adapter to FluentDriver.
 */
public class FluentTestRunnerAdapter extends FluentAdapter {
    private final SharedDriverStrategyReader sdsr;

    private final CookieStrategyReader csr;

    private final SharedMutator sharedMutator;
    protected SharedMutator staticSharedMutator;

    public FluentTestRunnerAdapter() {
        this(new DefaultDriverContainer());
    }

    public FluentTestRunnerAdapter(DriverContainer driverContainer) {
        this(driverContainer, new DefaultSharedDriverStrategyReader(), new DefaultCookieStrategyReader(), new DefaultSharedMutator());
    }

    public FluentTestRunnerAdapter(SharedDriverStrategyReader sharedDriverExtractor, CookieStrategyReader cookieExtractor) {
        this(new DefaultDriverContainer(), sharedDriverExtractor, cookieExtractor, new DefaultSharedMutator());
    }

    public FluentTestRunnerAdapter(SharedDriverStrategyReader sharedDriverExtractor, CookieStrategyReader cookieExtractor, SharedMutator sharedMutator) {
        this(new DefaultDriverContainer(), sharedDriverExtractor, cookieExtractor, sharedMutator);
    }

    public FluentTestRunnerAdapter(DriverContainer driverContainer, SharedDriverStrategyReader sharedDriverExtractor, CookieStrategyReader cookieExtractor, SharedMutator sharedMutator) {
        super(driverContainer);
        this.sdsr = sharedDriverExtractor;
        this.csr = cookieExtractor;
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
        SharedDriverStrategy strategy = sdsr.getSharedDriverStrategy(testClass, testName);

        SharedMutator.EffectiveParameters<?> sharedParameters = this.sharedMutator.getEffectiveParameters(testClass, testName, strategy);

        SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE.getOrCreateDriver(new Supplier<WebDriver>() {
            @Override
            public WebDriver get() {
                return FluentTestRunnerAdapter.this.newWebDriver();
            }
        }, sharedParameters.getTestClass(), sharedParameters.getTestName(), sharedParameters.getStrategy());

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
        SharedDriverStrategy strategy = sdsr.getSharedDriverStrategy(testClass, testName);

        if (strategy == SharedDriverStrategy.PER_METHOD) {
            SharedMutator.EffectiveParameters<?> sharedParameters = this.sharedMutator.getEffectiveParameters(testClass, testName, strategy);

            SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE.getDriver(sharedParameters.getTestClass(), sharedParameters.getTestName(), sharedParameters.getStrategy());
            if (sharedWebDriver != null) {
                SharedWebDriverContainer.INSTANCE.quit(sharedWebDriver);
            }
        } else if (csr.shouldDeleteCookies(testClass, testName)) {
            SharedMutator.EffectiveParameters<?> sharedParameters = this.sharedMutator.getEffectiveParameters(testClass, testName, strategy);

            SharedWebDriver sharedWebDriver = SharedWebDriverContainer.INSTANCE.getDriver(sharedParameters.getTestClass(), sharedParameters.getTestName(), sharedParameters.getStrategy());
            if (sharedWebDriver != null) {
                sharedWebDriver.getDriver().manage().deleteAllCookies();
            }
        }

        releaseFluent();

    }

    /**
     * Invoked when a test class has finished (whatever the success of failing status)
     *
     * @param testClass
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
        failed(null, getClass(), testName);
    }

    /**
     * Invoked when a test method has failed (before finished)
     *
     * @param testClass Test class
     */
    protected void failed(Class<?> testClass) {
        failed(null, testClass, testClass.getName());
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
        if (isFluentDriverAvailable()) {
            if (getScreenshotMode() == TriggerMode.AUTOMATIC_ON_FAIL && canTakeScreenShot()) {
                takeScreenShot(testClass.getSimpleName() + "_" + testName + ".png");
            }
            if (getHtmlDumpMode() == TriggerMode.AUTOMATIC_ON_FAIL && getDriver() != null) {
                takeHtmlDump(testClass.getSimpleName() + "_" + testName + ".html");
            }
        }
    }
}
