package io.fluentlenium;

import io.fluentlenium.adapter.testng.FluentTestNgSpringTest;
import io.fluentlenium.configuration.ConfigurationProperties;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class DontRunTestsWhenInitFailTest {

    private static class TestClass extends FluentTestNgSpringTest {

        TestClass() {
            getConfiguration().setScreenshotMode(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
            getConfiguration().setHtmlDumpMode(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
        }

        @Override
        public WebDriver newWebDriver() {
            throw new RuntimeException();
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

        verify(listenerAdapter, times(3)).onConfigurationFailure(any(ITestResult.class));
        verify(listenerAdapter, times(2)).onTestSkipped(any(ITestResult.class));
        verify(listenerAdapter, times(2)).onTestStart(any(ITestResult.class));
        verify(listenerAdapter, never()).onTestSuccess(any(ITestResult.class));
    }
}
