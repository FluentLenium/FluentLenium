package org.fluentlenium.adapter.testng.integration;

import org.fluentlenium.adapter.testng.FluentTestNg;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class DontRunTestsWhenInitFailTest {

    private static class TestClass extends FluentTestNg {

        TestClass() {
            getConfiguration().setScreenshotMode(TriggerMode.AUTOMATIC_ON_FAIL);
            getConfiguration().setHtmlDumpMode(TriggerMode.AUTOMATIC_ON_FAIL);
        }

        @Override
        public WebDriver newWebDriver() {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setHeadless(true);
            ChromeDriver driver = new ChromeDriver(chromeOptions);
            driver.get("invalid:url"); // Simulate a driver initialization failure.
            return driver;
        }

        @Ignore
        @Test
        public void testShouldBeIgnored() {
            Assert.fail("Should not be called");
        }

        @Test
        public void testDriverFailShouldNotCallTestMethod() {
            Assert.fail("Should not be called");
        }

        @Test
        public void testDriverFailShouldNotCallTestMethod2() {
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

        verify(listenerAdapter, never()).onTestSuccess(Mockito.any(ITestResult.class));
    }
}
