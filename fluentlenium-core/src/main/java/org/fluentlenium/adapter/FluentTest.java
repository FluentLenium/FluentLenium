/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.fluentlenium.adapter;

import org.fluentlenium.adapter.util.SharedDriverHelper;
import org.fluentlenium.adapter.util.ShutdownHook;
import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.FluentPage;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.Beta;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * All Junit Test should extends this class. It provides default parameters.
 */
public abstract class FluentTest extends FluentAdapter {
    protected enum Mode {TAKE_SNAPSHOT_ON_FAIL, NEVER_TAKE_SNAPSHOT;}

    private static WebDriver sharedDriver;
    public static boolean isSharedDriverPerClass;

    private Mode snapshotMode = Mode.NEVER_TAKE_SNAPSHOT;
    private String snapshotPath;


    public void setSnapshotPath(String path) {
        this.snapshotPath = path;
    }


    public void setSnapshotMode(Mode mode) {
        this.snapshotMode = mode;
    }

    @Rule
    public TestName name = new TestName();
    @Rule
    public MethodRule watchman = new TestWatchman() {
        @Override
        public void starting(FrameworkMethod method) {
            super.starting(method);
            if (SharedDriverHelper.isSharedDriverOnce(method.getMethod()
                    .getDeclaringClass())) {
                synchronized (this) {
                    if (sharedDriver == null) {
                        initFluentFromDefaultDriver();
                        sharedDriver = getDriver();
                        killTheBrowserOnShutdown();
                    } else {
                        initFluentWithExistingDriver();
                    }
                }
            } else if (SharedDriverHelper.isSharedDriverPerClass(method.getMethod()
                    .getDeclaringClass())) {
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
        public void finished(FrameworkMethod method) {
            super.finished(method);
            if (SharedDriverHelper.isSharedDriverPerMethod(method.getMethod()
                    .getDeclaringClass()) || SharedDriverHelper.isDefaultSharedDriver(method.getMethod()
                    .getDeclaringClass())) {
                quit();
            } else if (SharedDriverHelper.isDeleteCookies(method.getMethod().getDeclaringClass())) {
                if (sharedDriver != null) {
                    sharedDriver.manage().deleteAllCookies();
                }
            }
        }

        @Override
        public void failed(Throwable e, FrameworkMethod method) {
            if (snapshotMode == Mode.TAKE_SNAPSHOT_ON_FAIL) {
                takeScreenShot(snapshotPath + "/" + method.getMethod()
                        .getDeclaringClass().getSimpleName() + "_" + method.getName() + ".png");
            }
        }

    };

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

    /**
     * Override this method to change the driver
     *
     * @return
     */
    public WebDriver getDefaultDriver() {
        return new FirefoxDriver();
    }


    @AfterClass
    public static void afterClass() {
        if (isSharedDriverPerClass) {
            sharedDriver.quit();
            sharedDriver=null;
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
