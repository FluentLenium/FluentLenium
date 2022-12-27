package io.fluentlenium.smoketest;

import io.fluentlenium.IntegrationFluentTestNg;
import io.fluentlenium.IntegrationFluentTestNg;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class SmokeTestEventsDisabledTest extends IntegrationFluentTestNg {

    @BeforeClass
    public void setUp() {
        setEventsEnabled(false);
    }

    @Test
    public void smokeTest() {
        assertThat(getDriver()).isInstanceOf(ChromeDriver.class);
    }

}
