package org.fluentlenium.smoketest;

import org.fluentlenium.adapter.testng.FluentTestNgSpringTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class SmokeTestNewWebDriverOverrideTest extends FluentTestNgSpringTest {

    @Override
    public WebDriver newWebDriver() {
        return new HtmlUnitDriver();
    }

    @Test
    public void smokeTest() {
        assertThat(getDriver()).isInstanceOf(HtmlUnitDriver.class);
    }

}
