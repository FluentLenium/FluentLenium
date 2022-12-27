package io.fluentlenium.smoketest;

import io.fluentlenium.IntegrationFluentTestNg;
import io.fluentlenium.IntegrationFluentTestNg;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class SmokeTestEventsEnabledTest extends IntegrationFluentTestNg {

    @SuppressWarnings("ConstantConditions")
    @Test
    public void smokeTest() {
        assertThat(getDriver()).isInstanceOf(EventFiringWebDriver.class);
        EventFiringWebDriver driver = (EventFiringWebDriver) getDriver();
        assertThat(driver.getWrappedDriver()).isInstanceOf(ChromeDriver.class);
    }

}
