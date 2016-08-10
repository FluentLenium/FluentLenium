package org.fluentlenium.adapter;


import org.fluentlenium.adapter.util.CookieStrategyReader;
import org.fluentlenium.adapter.util.DefaultCookieStrategyReader;
import org.fluentlenium.adapter.util.DefaultSharedDriverStrategyReader;
import org.fluentlenium.adapter.util.SharedDriverOnceShutdownHook;
import org.fluentlenium.adapter.util.SharedDriverStrategy;
import org.fluentlenium.adapter.util.SharedDriverStrategyReader;
import org.openqa.selenium.WebDriver;

/**
 * Adapter used by any class based Test Runners adapters.
 */
public class FluentTestRunnerAdapter extends FluentAdapter {
    private static WebDriver sharedDriver;
    private static boolean isSharedDriverPerClass;

    private final SharedDriverStrategyReader sdsr;
    private final CookieStrategyReader csr;

    public FluentTestRunnerAdapter() {
        this(new DefaultDriverContainer());
    }

    public FluentTestRunnerAdapter(DriverContainer driverContainer) {
        this(driverContainer, new DefaultSharedDriverStrategyReader(), new DefaultCookieStrategyReader());
    }

    public FluentTestRunnerAdapter(SharedDriverStrategyReader sharedDriverExtractor, CookieStrategyReader cookieExtractor) {
        this(new DefaultDriverContainer(), sharedDriverExtractor, cookieExtractor);
    }

    public FluentTestRunnerAdapter(DriverContainer driverContainer, SharedDriverStrategyReader sharedDriverExtractor, CookieStrategyReader cookieExtractor) {
        super(driverContainer);
        this.sdsr = sharedDriverExtractor;
        this.csr = cookieExtractor;
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

        if (strategy == SharedDriverStrategy.ONCE) {
            synchronized (FluentTestRunnerAdapter.class) {
                if (sharedDriver == null) {
                    initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
                    sharedDriver = getDriver();
                    Runtime.getRuntime().addShutdownHook(new SharedDriverOnceShutdownHook("SharedDriver-ONCE-ShutdownHook"));
                } else {
                    initFluent(sharedDriver).withDefaultUrl(getDefaultBaseUrl());
                }
            }
        } else if (strategy == SharedDriverStrategy.PER_CLASS) {
            synchronized (FluentTestRunnerAdapter.class) {
                if (!isSharedDriverPerClass) {
                    initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
                    sharedDriver = getDriver();
                    isSharedDriverPerClass = true;
                } else {
                    initFluent(sharedDriver).withDefaultUrl(getDefaultBaseUrl());
                }
            }
        } else {
            initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
        }
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
            quit();
        } else if (sharedDriver != null && csr.shouldDeleteCookies(testClass, testName)) {
            sharedDriver.manage().deleteAllCookies();
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
        if (getScreenshotMode() == TriggerMode.ON_FAIL) {
            takeScreenShot(testClass.getSimpleName() + "_" +
                    testName + ".png");
        }
        if (getHtmlDumpMode() == TriggerMode.ON_FAIL) {
            takeHtmlDump(testClass.getSimpleName() + "_"
                    + testName + ".html");
        }
    }

    /**
     * Invoked when all methods from the class have been runned.
     */
    public static void releaseSharedDriver() {
        if (isSharedDriverPerClass) {
            sharedDriver.quit();
            sharedDriver = null;
            isSharedDriverPerClass = false;
        }
    }

}
