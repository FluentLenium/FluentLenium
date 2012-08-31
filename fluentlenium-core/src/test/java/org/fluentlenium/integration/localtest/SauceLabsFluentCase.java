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

package org.fluentlenium.integration.localtest;

import org.fluentlenium.adapter.FluentTest;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class SauceLabsFluentCase extends FluentTest {
   protected static final String DEFAULT_URL = "http://java-freelance.fr:8585/static/";
    public static final String JAVASCRIPT_URL = "http://localhost:8787/static/javascript.html";


    @Override
    public WebDriver getDefaultDriver() {
        DesiredCapabilities capabillities = new DesiredCapabilities(
                "firefox", "3.6.", Platform.WINDOWS);
        capabillities.setCapability("name", "Test of FluentLenium");
        WebDriver driver = null;
        try {
            driver = new RemoteWebDriver(
                    new URL("http://fluentlenium:8906940f-5638-4c29-beb6-c331df039f48@ondemand.saucelabs.com:80/wd/hub"),
                    capabillities);
        } catch (MalformedURLException e) {
        }
        return driver;
    }
}

