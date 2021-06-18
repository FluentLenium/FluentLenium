package org.fluentlenium.smoketest;

import org.fluentlenium.adapter.testng.FluentTestNgSpringTest;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class SmokeTestEventsEnabledTest extends FluentTestNgSpringTest {

    @Override
    public String getWebDriver() {
        return "htmlunit";
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void smokeTest() {
        assertThat(getDriver()).isInstanceOf(EventFiringWebDriver.class);
        EventFiringWebDriver driver = (EventFiringWebDriver) getDriver();
        assertThat(driver.getWrappedDriver()).isInstanceOf(HtmlUnitDriver.class);
    }

}
