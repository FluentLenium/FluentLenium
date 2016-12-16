package org.fluentlenium.adapter.testng.integration;

import org.fluentlenium.adapter.testng.FluentTestNg;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Test;

public class DontRunTestsWhenInitFailTest {

    private static class TestClass extends FluentTestNg {

        TestClass() {
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

        TestNG testNG = new TestNG(false);
        testNG.setTestClasses(new Class[] {TestClass.class});

        TestListenerAdapter listenerAdapter = Mockito.mock(TestListenerAdapter.class);
        testNG.addListener(listenerAdapter);

        testNG.run();

        Mockito.verify(listenerAdapter).onConfigurationFailure(Mockito.any(ITestResult.class));
        Mockito.verify(listenerAdapter).onTestSkipped(Mockito.any(ITestResult.class));
        Mockito.verify(listenerAdapter, Mockito.never()).onTestSuccess(Mockito.any(ITestResult.class));
    }

}
