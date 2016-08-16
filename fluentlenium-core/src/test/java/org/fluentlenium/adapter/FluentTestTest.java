package org.fluentlenium.adapter;

import org.apache.commons.io.FileUtils;
import org.assertj.core.util.Files;
import org.fluentlenium.adapter.util.DeleteCookies;
import org.fluentlenium.adapter.util.SharedDriver;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class FluentTestTest {
    private interface ScreenshotWebDriver extends WebDriver, TakesScreenshot {

    }

    private static List<WebDriver> drivers = new ArrayList<>();
    private static List<WebDriver> sharedClassDrivers = new ArrayList<>();
    private static List<WebDriver> sharedOnceDrivers = new ArrayList<>();

    private static List<ScreenshotWebDriver> screenshotWebDrivers = new ArrayList<>();
    private static File screenshotFile;
    private static String html = "<html>FluentLenium</html>";
    private static byte[] screenshotData = {1,4,7,9,2,4,2,4,3};

    private static File tmpPath = Files.newTemporaryFolder();

    private static List<WebDriver.Options> sharedClassDriversOptions = new ArrayList<>();

    public static class InternalTest extends FluentTest {
        @Override
        public WebDriver getDefaultDriver() {
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

    @SharedDriver(SharedDriver.SharedType.PER_CLASS)
    public static class InternalTestSharedClass extends FluentTest {
        @Override
        public WebDriver getDefaultDriver() {
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

    @SharedDriver(SharedDriver.SharedType.ONCE)
    public static class InternalTestSharedOnce extends FluentTest {
        @Override
        public WebDriver getDefaultDriver() {
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

    @SharedDriver(SharedDriver.SharedType.PER_CLASS)
    @DeleteCookies
    public static class ShouldDeleteCookiesTest extends FluentTest {
        @Override
        public WebDriver getDefaultDriver() {
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
            getConfiguration().setHtmlDumpMode(TriggerMode.ON_FAIL);
            getConfiguration().setScreenshotPath(tmpPath.getPath());
            getConfiguration().setScreenshotMode(TriggerMode.ON_FAIL);
        }

        @Override
        public WebDriver getDefaultDriver() {
            ScreenshotWebDriver webDriver = Mockito.mock(ScreenshotWebDriver.class);
            try {
                screenshotFile = File.createTempFile("FluentTestTest.java", "");
                FileUtils.writeByteArrayToFile(screenshotFile, screenshotData);
                screenshotFile.deleteOnExit();
            } catch (IOException e) {
                throw new IOError(e);
            }

            Mockito.when(webDriver.getScreenshotAs(OutputType.FILE)).thenReturn(screenshotFile);

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

    @After
    public void after() {
        drivers.clear();
        sharedClassDrivers.clear();
        sharedOnceDrivers.clear();
        screenshotWebDrivers.clear();
        SharedWebDriverContainer.INSTANCE.quitAll();
    }

    @Test
    public void testFluentTest() {
        Result result = JUnitCore.runClasses(InternalTest.class);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getMessage()).isEqualTo("Failing Test");

        assertThat(drivers).hasSize(3);

        for (WebDriver driver : drivers) {
            Mockito.verify(driver).quit();
        }

        assertThat(SharedWebDriverContainer.INSTANCE.getTestClassDrivers(InternalTest.class)).isEmpty();
    }

    @Test
    public void testInternalTestSharedClass() {
        Result result = JUnitCore.runClasses(InternalTestSharedClass.class);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getMessage()).isEqualTo("Failing Test");

        assertThat(sharedClassDrivers).hasSize(1);

        for (WebDriver driver : sharedClassDrivers) {
            Mockito.verify(driver).quit();
        }

        assertThat(SharedWebDriverContainer.INSTANCE.getTestClassDrivers(InternalTest.class)).isEmpty();
    }

    @Test
    public void testInternalTestSharedOnce() {
        Result result = JUnitCore.runClasses(InternalTestSharedOnce.class);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getMessage()).isEqualTo("Failing Test");

        assertThat(sharedOnceDrivers).hasSize(1);

        for (WebDriver driver : sharedOnceDrivers) {
            Mockito.verify(driver, Mockito.never()).quit();
        }

        assertThat(SharedWebDriverContainer.INSTANCE.getAllDrivers()).hasSize(1);
    }

    @Test
    public void testShouldDeleteCookiesTest() {
        Result result = JUnitCore.runClasses(ShouldDeleteCookiesTest.class);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getMessage()).isEqualTo("Failing Test");

        assertThat(sharedClassDrivers).hasSize(1);

        for (WebDriver driver : sharedClassDrivers) {
            Mockito.verify(driver).quit();
        }

        for(WebDriver.Options options : sharedClassDriversOptions) {
            Mockito.verify(options, Mockito.times(3)).deleteAllCookies();
        }

        assertThat(SharedWebDriverContainer.INSTANCE.getAllDrivers()).isEmpty();
    }

    @Test
    public void testAutomaticScreenShotTest() throws IOException {
        Result result = JUnitCore.runClasses(AutomaticScreenShotTest.class);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getMessage()).isEqualTo("Failing Test");

        assertThat(screenshotWebDrivers).hasSize(1);

        ScreenshotWebDriver driver = screenshotWebDrivers.get(0);

        Mockito.verify(driver).getScreenshotAs(OutputType.FILE);
        Mockito.verify(driver).findElements(By.cssSelector("html"));

        assertThat(tmpPath.list()).contains("AutomaticScreenShotTest_failingTest(org.fluentlenium.adapter.FluentTestTest$AutomaticScreenShotTest).html");
        assertThat(tmpPath.list()).contains("AutomaticScreenShotTest_failingTest(org.fluentlenium.adapter.FluentTestTest$AutomaticScreenShotTest).png");

        File screenshotGeneratedFile = new File(tmpPath, "AutomaticScreenShotTest_failingTest(org.fluentlenium.adapter.FluentTestTest$AutomaticScreenShotTest).png");
        File htmlDumpFile = new File(tmpPath, "AutomaticScreenShotTest_failingTest(org.fluentlenium.adapter.FluentTestTest$AutomaticScreenShotTest).html");

        try {
            assertThat(FileUtils.readFileToByteArray(screenshotGeneratedFile)).isEqualTo(screenshotData);
            assertThat(FileUtils.readFileToString(htmlDumpFile)).isEqualTo(html);
        } finally {
            FileUtils.deleteQuietly(screenshotGeneratedFile);
            FileUtils.deleteQuietly(htmlDumpFile);
        }

    }
}
