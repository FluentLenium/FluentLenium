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
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * All TestNG Test should extends this class. It provides default parameters.
 */
public abstract class FluentTestNg extends FluentAdapter {

    private static WebDriver sharedDriverOnce;

    public FluentTestNg() {
        super();
    }

    @BeforeClass
    public void beforeClass() {
        Class<? extends FluentTestNg> aClass = this.getClass();
        if (SharedDriverHelper.isSharedDriverOnce(aClass)) {
            synchronized (this) {
                if (sharedDriverOnce == null) {
                    this.initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
                    sharedDriverOnce = this.getDriver();
                    Runtime.getRuntime().addShutdownHook(new ShutdownHook("fluentlenium", this));
                } else {
                    this.initFluent(sharedDriverOnce).withDefaultUrl(getDefaultBaseUrl());
                }
                initTest();
            }
        } else if (SharedDriverHelper.isSharedDriverPerClass(this.getClass())) {
            this.initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
            initTest();
        }

    }

    @BeforeMethod
    public void beforeMethod() {
        if (SharedDriverHelper.isSharedDriverPerMethod(this.getClass()) || SharedDriverHelper.isDefaultSharedDriver(this.getClass())) {
            this.initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
            initTest();
        }

    }

    @AfterMethod
    public void afterMethod() {
        cleanUpTest();
        if (SharedDriverHelper.isSharedDriverPerMethod(this.getClass())|| SharedDriverHelper.isDefaultSharedDriver(this.getClass())) {
            quit();
        } else if (SharedDriverHelper.isDeleteCookies(this.getClass())) {
            this.getDriver().manage().deleteAllCookies();
        }

    }

    @AfterClass
    public void afterClass() {
        if (SharedDriverHelper.isSharedDriverPerClass(this.getClass())) {
            quit();
        }
    }

}
