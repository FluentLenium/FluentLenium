package org.fluentlenium.adapter;


import org.fluentlenium.adapter.util.CookieStrategyReader;
import org.fluentlenium.adapter.util.DefaultCookieStrategyReader;
import org.fluentlenium.adapter.util.DefaultSharedDriverStrategyReader;
import org.fluentlenium.adapter.util.SharedDriverStrategyReader;
import org.fluentlenium.adapter.util.SharedDriverStrategy;
import org.fluentlenium.adapter.util.ShutdownHook;
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
        this(new DefaultSharedDriverStrategyReader(), new DefaultCookieStrategyReader());
    }

    public FluentTestRunnerAdapter(SharedDriverStrategyReader sharedDriverExtractor, CookieStrategyReader cookieExtractor) {
        this.sdsr = sharedDriverExtractor;
        this.csr = cookieExtractor;
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
            synchronized (this) {
                if (sharedDriver == null) {
                    initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
                    sharedDriver = getDriver();
                    Runtime.getRuntime().addShutdownHook(new ShutdownHook("fluentlenium", this));
                } else {
                    initFluent(sharedDriver).withDefaultUrl(getDefaultBaseUrl());
                }
            }
        } else if (strategy == SharedDriverStrategy.PER_CLASS) {
            synchronized (this) {
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

        init();
    }

    /**
     * Invoked when a test method has finished (whatever the success of failing status)
     *
     * @param testClass Test class
     * @param testName  Test name
     */
    protected void finished(Class<?> testClass, String testName) {
        close();
        SharedDriverStrategy strategy = sdsr.getSharedDriverStrategy(testClass, testName);
        if (strategy == SharedDriverStrategy.PER_METHOD) {
            quit();
        } else if (sharedDriver != null && csr.shouldDeleteCookies(testClass, testName)) {
            sharedDriver.manage().deleteAllCookies();
        }
    }

    /**
     * Invoked when a test method has failed (before finished)
     *
     * @param e         Throwable thrown by the failing test.
     * @param testClass Test class
     * @param testName  Test name
     */
    protected void failed(Throwable e, Class<?> testClass, String testName) {
        if (screenshotMode == TriggerMode.ON_FAIL) {
            takeScreenShot(testClass.getSimpleName() + "_" +
                    testName + ".png");
        }
        if (htmlDumpMode == TriggerMode.ON_FAIL) {
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
