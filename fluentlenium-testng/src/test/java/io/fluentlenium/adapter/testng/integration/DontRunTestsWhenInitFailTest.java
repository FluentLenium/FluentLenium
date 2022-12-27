package io.fluentlenium.adapter.testng.integration;

import io.fluentlenium.adapter.testng.FluentTestNg;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DontRunTestsWhenInitFailTest {

    private static class TestClass extends FluentTestNg {

        TestClass() {
            getConfiguration().setScreenshotMode(TriggerMode.AUTOMATIC_ON_FAIL);
            getConfiguration().setHtmlDumpMode(TriggerMode.AUTOMATIC_ON_FAIL);
        }

        @Override
        public WebDriver newWebDriver() {
            throw new IllegalStateException();
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
        testNG.setTestClasses(new Class[]{TestClass.class});

        TestListenerAdapter listenerAdapter = Mockito.mock(TestListenerAdapter.class);
        testNG.addListener(listenerAdapter);

        testNG.run();

        verify(listenerAdapter, times(2)).onConfigurationFailure(Mockito.any(ITestResult.class));
        verify(listenerAdapter, times(2)).onTestSkipped(Mockito.any(ITestResult.class));
        verify(listenerAdapter, times(2)).onTestStart(Mockito.any(ITestResult.class));
        verify(listenerAdapter, never()).onTestSuccess(Mockito.any(ITestResult.class));
    }
}
