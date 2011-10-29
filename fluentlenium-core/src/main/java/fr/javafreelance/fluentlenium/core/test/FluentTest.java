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

package fr.javafreelance.fluentlenium.core.test;

import fr.javafreelance.fluentlenium.core.Fluent;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * All Junit Test should extends this class. It provides default parameters.
 */
public abstract class FluentTest extends Fluent {
     @Before
     public final void before(){
         this.setDriver(getDefaultDriver());
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

    public WebDriverWait getDefaultWait() {
        return new WebDriverWait(getDefaultDriver(), 5);
    }

    /**
     * Open the url page
     *
     * @param url
     */
    public void goTo(String url) {
        getDriver().get(url);
    }


    @After
    public void after() {

        getDriver().quit();
    }


}
