package org.fluentlenium.adapter.junit.jupiter.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

import net.jcip.annotations.NotThreadSafe;
import org.apache.commons.io.FileUtils;
import org.assertj.core.util.Files;
import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.adapter.junit.jupiter.MockitoExtension;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.configuration.FluentConfiguration.BooleanValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@NotThreadSafe
public class FluentTestTest {
    private static List<WebDriver> drivers = new ArrayList<>();
    private static List<WebDriver> sharedClassDrivers = new ArrayList<>();
    private static List<WebDriver> sharedOnceDrivers = new ArrayList<>();

    private static List<ScreenshotWebDriver> screenshotWebDrivers = new ArrayList<>();
    private static String html = "<html>FluentLenium</html>";
    private static byte[] screenshotData = {1, 4, 7, 9, 2, 4, 2, 4, 3};

    private static File tmpPath = Files.newTemporaryFolder();

    private static List<WebDriver.Options> sharedClassDriversOptions = new ArrayList<>();

    private interface ScreenshotWebDriver extends WebDriver, TakesScreenshot {
    }

    public static class InternalTest extends FluentTest {
        @Override
        public WebDriver newWebDriver() {
            WebDriver webDriver = Mockito.mock(WebDriver.class);
            drivers.add(webDriver);
            return webDriver;
        }

        @Test
        public void okTest() {
            goTo("url");
        }

        @Test
        public void okTest2() {
            goTo("url2");
        }

        @Test
        public void failingTest() {
            fail("Failing Test");
        }
    }

    @FluentConfiguration(driverLifecycle = DriverLifecycle.CLASS)
    @ExtendWith(MockitoExtension.class)
    public static class InternalTestSharedClass extends FluentTest {
        @Override
        public WebDriver newWebDriver() {
            WebDriver webDriver = Mockito.mock(WebDriver.class);
            sharedClassDrivers.add(webDriver);
            return webDriver;
        }

        @Test
        public void okTest() {
            goTo("url");
        }

        @Test
        public void okTest2() {
            goTo("url2");
        }

        @Test
        public void failingTest() {
            fail("Failing Test");
        }
    }

    @FluentConfiguration(driverLifecycle = DriverLifecycle.JVM)
    public static class InternalTestSharedOnce extends FluentTest {
        @Override
        public WebDriver newWebDriver() {
            WebDriver webDriver = Mockito.mock(WebDriver.class);
            sharedOnceDrivers.add(webDriver);
            return webDriver;
        }

        @Test
        public void okTest() {
            goTo("url");
        }

        @Test
        public void okTest2() {
            goTo("url2");
        }

        @Test
        public void failingTest() {
            fail("Failing Test");
        }
    }

    @FluentConfiguration(driverLifecycle = DriverLifecycle.CLASS, deleteCookies = BooleanValue.TRUE)
    public static class ShouldDeleteCookiesTest extends FluentTest {
        @Override
        public WebDriver newWebDriver() {
            WebDriver webDriver = Mockito.mock(WebDriver.class);

            WebDriver.Options options = Mockito.mock(WebDriver.Options.class);
            sharedClassDriversOptions.add(options);
            Mockito.when(webDriver.manage()).thenReturn(options);

            sharedClassDrivers.add(webDriver);
            return webDriver;
        }

        @Test
        public void okTest() {
            goTo("url");
        }

        @Test
        public void okTest2() {
            goTo("url2");
        }

        @Test
        public void failingTest() {
            fail("Failing Test");
        }
    }

    public static class AutomaticScreenShotTest extends FluentTest {

        public AutomaticScreenShotTest() {
            getConfiguration().setHtmlDumpPath(tmpPath.getPath());
            getConfiguration().setHtmlDumpMode(TriggerMode.AUTOMATIC_ON_FAIL);
            getConfiguration().setScreenshotPath(tmpPath.getPath());
            getConfiguration().setScreenshotMode(TriggerMode.AUTOMATIC_ON_FAIL);
        }

        @Override
        public WebDriver newWebDriver() {
            try {
                File screenshotFile = File.createTempFile("FluentTestTest.java", "");
                FileUtils.writeByteArrayToFile(screenshotFile, screenshotData);
                screenshotFile.deleteOnExit();
            } catch (IOException e) {
                throw new IOError(e);
            }

            ScreenshotWebDriver webDriver = Mockito.mock(ScreenshotWebDriver.class);
            byte[] screenshot = new byte[20];
            new Random().nextBytes(screenshot);
            Mockito.when(webDriver.getScreenshotAs(OutputType.BYTES)).thenReturn(screenshotData);

            WebElement htmlElement = Mockito.mock(WebElement.class);
            Mockito.when(htmlElement.getAttribute("innerHTML")).thenReturn(html);

            Mockito.when(webDriver.findElements(By.cssSelector("html"))).thenReturn(Arrays.asList(htmlElement));
            screenshotWebDrivers.add(webDriver);
            return webDriver;
        }

        @Test
        public void failingTest() {
            fail("Failing Test");
        }
    }

    @AfterEach
    public void after() {
        drivers.clear();
        sharedClassDrivers.clear();
        sharedOnceDrivers.clear();
        screenshotWebDrivers.clear();
        SharedWebDriverContainer.INSTANCE.quitAll();
    }

    @Test
    public void testFluentTest() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(InternalTest.class)).build();

        class Listener implements TestExecutionListener {
            public List<Throwable> failures = new ArrayList<>();

            @Override
            public void executionFinished(TestIdentifier identifier, TestExecutionResult result) {
                result.getThrowable().ifPresent(throwable -> failures.add(throwable));
            }
        }

        final Listener listener = new Listener();

        Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        assertThat(listener.failures).hasSize(1);
        assertThat(listener.failures.get(0).getMessage()).isEqualTo("Failing Test");

        assertThat(drivers).hasSize(3);

        for (WebDriver driver : drivers) {
            Mockito.verify(driver).quit();
        }

        assertThat(SharedWebDriverContainer.INSTANCE.getTestClassDrivers(InternalTest.class)).isEmpty();
    }

    @Test
    public void testInternalTestSharedClass() {
        SummaryGeneratingListener summaryGeneratingListener = getSummaryGeneratingListener(InternalTestSharedClass.class);

        assertThat(summaryGeneratingListener.getSummary().getFailures()).hasSize(1);
        assertThat(summaryGeneratingListener.getSummary().getFailures().get(0).getException().getMessage())
                .isEqualTo("Failing Test");

        assertThat(sharedClassDrivers).hasSize(1);

        for (WebDriver driver : sharedClassDrivers) {
            Mockito.verify(driver).quit();
        }

        assertThat(SharedWebDriverContainer.INSTANCE.getTestClassDrivers(InternalTest.class)).isEmpty();
    }

    private SummaryGeneratingListener getSummaryGeneratingListener(Class<? extends FluentTest> testClass) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(testClass)).build();

        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener summaryGeneratingListener
                = new SummaryGeneratingListener();

        launcher.execute(request, summaryGeneratingListener);
        return summaryGeneratingListener;
    }

    @Test
    public void testInternalTestSharedOnce() {
        SummaryGeneratingListener summaryGeneratingListener = getSummaryGeneratingListener(InternalTestSharedOnce.class);
        assertThat(summaryGeneratingListener.getSummary().getFailures()).hasSize(1);
        assertThat(summaryGeneratingListener.getSummary().getFailures().get(0).getException().getMessage())
                .isEqualTo("Failing Test");

        assertThat(sharedOnceDrivers).hasSize(1);

        for (WebDriver driver : sharedOnceDrivers) {
            Mockito.verify(driver, Mockito.never()).quit();
        }

        assertThat(SharedWebDriverContainer.INSTANCE.getAllDrivers()).hasSize(1);
    }

    @Test
    public void testShouldDeleteCookiesTest() {
        SummaryGeneratingListener summaryGeneratingListener =
                getSummaryGeneratingListener(ShouldDeleteCookiesTest.class);
        assertThat(summaryGeneratingListener.getSummary().getFailures()).hasSize(1);
        assertThat(summaryGeneratingListener.getSummary().getFailures().get(0).getException().getMessage())
                .isEqualTo("Failing Test");

        assertThat(sharedClassDrivers).hasSize(1);

        for (WebDriver driver : sharedClassDrivers) {
            Mockito.verify(driver).quit();
        }

        for (WebDriver.Options options : sharedClassDriversOptions) {
            Mockito.verify(options, Mockito.times(3)).deleteAllCookies();
        }

        assertThat(SharedWebDriverContainer.INSTANCE.getAllDrivers()).isEmpty();
    }

    @Test
    public void testAutomaticScreenShotTest() throws IOException {
        SummaryGeneratingListener summaryGeneratingListener =
                getSummaryGeneratingListener(AutomaticScreenShotTest.class);

        assertAll("summary",
                () -> assertThat(summaryGeneratingListener.getSummary().getFailures()).hasSize(1),
                () -> assertThat(summaryGeneratingListener.getSummary().getFailures().get(0)
                        .getException().getMessage()).isEqualTo("Failing Test"));

        assertThat(screenshotWebDrivers).hasSize(1);

        ScreenshotWebDriver driver = screenshotWebDrivers.get(0);

        Mockito.verify(driver).getScreenshotAs(OutputType.BYTES);
        Mockito.verify(driver).findElements(By.cssSelector("html"));

        assertThat(tmpPath.list()).contains("AutomaticScreenShotTest_failingTest.html");
        assertThat(tmpPath.list()).contains("AutomaticScreenShotTest_failingTest.png");

        File screenshotGeneratedFile = new File(tmpPath, "AutomaticScreenShotTest_failingTest.png");
        File htmlDumpFile = new File(tmpPath, "AutomaticScreenShotTest_failingTest.html");

        try {
            assertThat(FileUtils.readFileToByteArray(screenshotGeneratedFile)).isEqualTo(screenshotData);
            assertThat(FileUtils.readFileToString(htmlDumpFile, Charset.defaultCharset())).isEqualTo(html);
        } finally {
            FileUtils.deleteQuietly(screenshotGeneratedFile);
            FileUtils.deleteQuietly(htmlDumpFile);
        }

    }
}
