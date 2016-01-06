package org.fluentlenium.adapter;

import org.fluentlenium.adapter.util.ShutdownHook;
import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.FluentPage;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.Beta;
import org.openqa.selenium.WebDriver;

import static org.fluentlenium.adapter.util.SharedDriverHelper.*;

/**
 * All Junit Test should extends this class. It provides default parameters.
 */
public abstract class FluentTest extends FluentAdapter {
    protected enum Mode {TAKE_SNAPSHOT_ON_FAIL, NEVER_TAKE_SNAPSHOT}

    private static WebDriver sharedDriver;
    private static boolean isSharedDriverPerClass;

    private Mode snapshotMode = Mode.NEVER_TAKE_SNAPSHOT;
    private String snapshotPath = "default";


    @Rule
    public TestName name = new TestName();
    @Rule
    public TestRule watchman = new TestWatcher() {
        @Override
        public void starting(Description description) {
            super.starting(description);
            //TODO Refactor
            if (isSharedDriverOnce(description.getTestClass())) {
                synchronized (this) {
                    if (sharedDriver == null) {
                        initFluentFromDefaultDriver();
                        sharedDriver = getDriver();
                        killTheBrowserOnShutdown();
                    } else {
                        initFluentWithExistingDriver();
                    }
                }
            } else if (isSharedDriverPerClass(description.getTestClass())) {
                synchronized (this) {
                    if (!isSharedDriverPerClass) {
                        initFluentFromDefaultDriver();
                        sharedDriver = getDriver();
                        isSharedDriverPerClass = true;
                    } else {
                        initFluentWithExistingDriver();
                    }
                }
            } else {
                initFluentFromDefaultDriver();
            }

            initTest();
            setDefaultConfig();
        }


        @Override
        public void finished(Description description) {
            super.finished(description);
            if (isSharedDriverPerMethod(description.getTestClass()) ||
                    isDefaultSharedDriver(description.getTestClass())) {
                quit();
            } else if (isDeleteCookies(description.getTestClass())) {
                if (sharedDriver != null) {
                    sharedDriver.manage().deleteAllCookies();
                }
            }
        }

        @Override
        public void failed(Throwable e, Description description) {
            if (snapshotMode == Mode.TAKE_SNAPSHOT_ON_FAIL) {
                takeScreenShot(snapshotPath + "/" + description.getTestClass().getSimpleName() + "_" +
                        description.getMethodName() + ".png");
            }
        }

    };

    public void setSnapshotPath(String path) {
        this.snapshotPath = path;
    }


    public void setSnapshotMode(Mode mode) {
        this.snapshotMode = mode;
    }

    private void killTheBrowserOnShutdown() {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook("fluentlenium", this));
    }

    private void initFluentWithExistingDriver() {
        initFluent(sharedDriver).withDefaultUrl(getDefaultBaseUrl());
    }

    private void initFluentFromDefaultDriver() {
        initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
    }


    public FluentTest() {
        super();
    }


    @AfterClass
    public static void afterClass() {
        if (isSharedDriverPerClass) {
            sharedDriver.quit();
            sharedDriver = null;
            isSharedDriverPerClass = false;
        }
    }

    /**
     * Override this method to change the default time to wait for a page to be loaded
     */
    @Beta
    public void setDefaultConfig() {
    }

    public static void assertAt(FluentPage fluent) {
        fluent.isAt();
    }


}
