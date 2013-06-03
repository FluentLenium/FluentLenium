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
package org.fluentlenium.cucumber.adapter;

import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.cucumber.adapter.util.SharedDriverHelper;
import org.fluentlenium.cucumber.adapter.util.ShutdownHook;
import org.openqa.selenium.WebDriver;

public class FluentCucumberAdapter extends FluentAdapter {
    public static boolean isSharedDriverPerScenario;
    public static WebDriver sharedDriver = null;
    private Mode snapshotMode = Mode.NEVER_TAKE_SNAPSHOT;
    private String snapshotPath;

    public void setSnapshotPath(String path) {
        this.snapshotPath = path;
    }

    public void setSnapshotMode(Mode mode) {
        this.snapshotMode = mode;
    }

    protected Fluent initFluentWithWebDriver(final FluentCucumberAdapter initializer) {
        return initFluentWithWebDriver(initializer, initializer.getDriver());
    }



    protected Fluent initFluentWithWebDriver(final FluentCucumberAdapter initializer, WebDriver driver) {
        Class clazz = initializer.getClass();

        if (SharedDriverHelper.isSharedDriverPerFeature(clazz)) {
            synchronized (this) {
                if (sharedDriver == null) {
                    initSharedDriver(driver);
                    killTheBrowserOnShutdown();
                } else {
                    initFluentWithExistingDriver();
                }
            }
        } else if (SharedDriverHelper.isSharedDriverPerScenario(clazz) || SharedDriverHelper.isDefaultSharedDriver(clazz)) {
            synchronized (this) {
                if (!isSharedDriverPerScenario) {
                    initSharedDriver(driver);
                    isSharedDriverPerScenario = true;
                } else {
                    initFluentWithExistingDriver();
                }
            }
        } else {
            initFluentFromDefaultDriver();
        }
        return this;
    }

    private void initSharedDriver(WebDriver driver) {
        if (driver != null) {
            sharedDriver = driver;
            initFluentWithExistingDriver();
        } else {
            initFluentFromDefaultDriver();
        }
        sharedDriver = getDriver();
    }

    protected Fluent initFluent() {
        return initFluentWithWebDriver(this);
    }

    @Override
    protected Fluent initFluent(WebDriver driver) {
        if (sharedDriver == null) {
            sharedDriver = driver;
            initFluentWithExistingDriver();
            killTheBrowserOnShutdown();
        } else {
            initFluentWithExistingDriver();
        }
        return this;
    }

    private void initFluentWithExistingDriver() {
        super.initFluent(sharedDriver).withDefaultUrl(getDefaultBaseUrl());
    }

    private void initFluentFromDefaultDriver() {
        super.initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
    }

    @Override
    public void quit() {
        if (isSharedDriverPerScenario) {
            sharedDriver.quit();
            sharedDriver = null;
            isSharedDriverPerScenario = false;
        }
    }

    public void forceQuit() {
        super.quit();
    }

    private void killTheBrowserOnShutdown() {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook("fluentlenium", this));
    }

    protected enum Mode {TAKE_SNAPSHOT_ON_FAIL, NEVER_TAKE_SNAPSHOT;}

}
