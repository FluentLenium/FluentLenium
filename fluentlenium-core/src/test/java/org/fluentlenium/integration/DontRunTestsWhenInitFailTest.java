package org.fluentlenium.integration;

import org.assertj.core.api.Assertions;
import org.fluentlenium.integration.util.adapter.FluentTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class DontRunTestsWhenInitFailTest {

    public static class IgnoreTestClass extends FluentTest {

        public IgnoreTestClass() {
            getConfiguration().setScreenshotMode(TriggerMode.AUTOMATIC_ON_FAIL);
            getConfiguration().setHtmlDumpMode(TriggerMode.AUTOMATIC_ON_FAIL);
        }

        @Override
        public WebDriver newWebDriver() {
            HtmlUnitDriver driver = new HtmlUnitDriver(false);
            driver.get("invalid:url"); // Simulate a driver initialization failure.
            return driver;
        }

        @Test
        public void testDriverFailShouldNotCallTestMethod() {
            Assert.fail("Should not be called");
        }
    }

    @Test
    public void testRun() {
        JUnitCore junit = new JUnitCore();
        Result run = junit.run(IgnoreTestClass.class);

        Assertions.assertThat(run.getFailures()).hasSize(1);
        Assertions.assertThat(run.getFailures().get(0).getMessage())
                .startsWith("java.net.MalformedURLException");
    }

}
