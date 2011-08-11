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
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static fr.javafreelance.fluentlenium.core.filter.FilterConstructor.*;
import static org.fest.assertions.Assertions.assertThat;

public class SelectorOnLabsTest extends LocalFluentCase {
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

    @Test
    public void checkTagSelector() {
        goTo(DEFAULT_URL);
        assertThat($("h1")).hasSize(1);
    }

    @Test
    public void checkIdSelector() {
        goTo(DEFAULT_URL);
        assertThat($("#oneline")).hasSize(1);
    }

    @Test
    public void checkCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small")).hasSize(3);
    }

    @Test
    public void checkWithNameCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
    }

    @Test
    public void checkWithNameMatcherCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName(contains("name")))).hasSize(2);
    }

    @Test
    public void checkWithNameMatcherCssPatternSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName(contains(regex("na?me[0-9]*")))).getNames()).contains("name", "name2");
    }

    @Test
    public void checkWithNameMatcherCssNotContainPatternSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName(notContains(regex("na?me[0-9]*")))).getNames()).hasSize(1);
    }

    @Test
    public void checkWithNameEqualMatcherCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName(equal("name")))).hasSize(1);
    }

    @Test
    public void checkWithNameMatcherNotContainsCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName(notContains("toto")))).hasSize(3);
    }

    @Test
    public void checkWithIdCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withId("id"))).hasSize(1);
    }

    @Test
    public void checkWithTextCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withText("Small 2"))).hasSize(1);
    }

    @Test
    public void checkSelectAttributeAction() {
        goTo(DEFAULT_URL);
        assertThat($(".small", 2).getText()).isEqualTo("Small 3");
    }


    @Test
    public void checkCustomSelectAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("generated", "true")).getTexts()).contains("Test custom attribute");
    }

    @Test
    public void checkCustomSelectAttributeWithRegex() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("generated", contains(regex("t?ru?")))).getTexts()).contains("Test custom attribute");
    }

    @Test
    public void checkCustomSelectAttributeIfText() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("TEXT", "Pharmacy")).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkCustomSelectAttributeIfTextIsInLowerCase() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("text", "Pharmacy")).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkStartAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(startsWith("na"))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkStartAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(startsWith(regex("na?")))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkStartAttributeMatcherNotFind() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(startsWith(regex("am"))))).hasSize(0);
    }

    @Test
    public void checkEndAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(endsWith("me")))).hasSize(1);
        assertThat($("span", withName(endsWith("name"))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkEndAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(endsWith(regex("na[me]*")))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkEndAttributeMatcherNotFind() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(endsWith(regex("am?"))))).hasSize(0);
    }


}
