package org.fluentlenium.integration;

import org.assertj.core.api.Assertions;
import org.fluentlenium.adapter.FluentTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class DontRunTestsWhenInitFailTest {

    public static class TestClass extends FluentTest {

        public TestClass() {
            getConfiguration().setScreenshotMode(TriggerMode.ON_FAIL);
            getConfiguration().setHtmlDumpMode(TriggerMode.ON_FAIL);
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
        Result run = junit.run(TestClass.class);

        Assertions.assertThat(run.getFailures()).hasSize(1);
        Assertions.assertThat(run.getFailures().get(0).getMessage())
                .startsWith("java.net.MalformedURLException");
    }

}
