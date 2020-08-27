package org.fluentlenium.smoketest;

import org.fluentlenium.adapter.testng.FluentTestNgSpringTest;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class SmokeTestEventsDisabledTest extends FluentTestNgSpringTest {

    @Override
    public String getWebDriver() {
        return "htmlunit";
    }

    @BeforeClass
    public void setUp() {
        setEventsEnabled(false);
    }

    @Test
    public void smokeTest() {
        assertThat(getDriver()).isInstanceOf(HtmlUnitDriver.class);
    }

}
