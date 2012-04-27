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

import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.FluentPage;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * All Junit Test should extends this class. It provides default parameters.
 */
public abstract class FluentTest extends FluentAdapter {

    protected enum Mode {TAKE_SNAPSHOT_ON_FAIL, NEVER_TAKE_SNAPSHOT}

    private Mode snapshotMode = Mode.NEVER_TAKE_SNAPSHOT;
    private String snapshotPath;
    public Class classe = this.getClass();

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
            initFluent(getDefaultDriver());
            initTest();
        }

        @Override
        public void finished(FrameworkMethod method) {
            super.finished(method);
            if (getDriver() != null) {
                quit();
            }
        }

        @Override
        public void failed(Throwable e, FrameworkMethod method) {
            if (snapshotMode == Mode.TAKE_SNAPSHOT_ON_FAIL) {
                takeScreenShot(snapshotPath+"/"+classe.getSimpleName() + "_" + method.getName() +".png");
            }
        }

    };

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


    public static void assertAt(FluentPage fluent) {
        fluent.isAt();
    }


}
