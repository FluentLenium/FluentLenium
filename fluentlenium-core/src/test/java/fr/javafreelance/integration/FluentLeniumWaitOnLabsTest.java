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

package fr.javafreelance.integration;


import fr.javafreelance.integration.localTest.LocalFluentCase;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class FluentLeniumWaitOnLabsTest extends LocalFluentCase {
    protected static final String DEFAULT_URL = "http://java-freelance.fr:8585/static/";


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
    @Before
    public void before() {
        goTo(DEFAULT_URL);

    }

    @Test
    public void checkAwaitContainsNameWithNameMatcher() {

        await().atMost(1, NANOSECONDS).until(".small").with("name").contains("name").isPresent();
    }

    @Test
    public void checkAwaitContainsIdWithIdMatcher() {

        await().atMost(1, NANOSECONDS).until(".small").with("id").contains("id2").isPresent();
    }

    @Test
    public void checkAwaitStartWith() {

        await().atMost(1, NANOSECONDS).until(".small").with("id").startsWith("id").hasSize(2);
    }

    @Test
    public void checkAwaitEndsWith() {

        await().atMost(1, NANOSECONDS).until(".small").with("id").endsWith("2").hasSize(1);
    }
}
