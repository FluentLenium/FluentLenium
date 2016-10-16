package org.fluentlenium.adapter;

import org.apache.commons.io.FileUtils;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentTestRunnerAdapterTest {
    @Mock
    private TestWebDriver driver;

    private interface TestWebDriver extends WebDriver, TakesScreenshot {
    }

    @Test
    public void testStartingFinish() {
        final FluentTestRunnerAdapter adapter = spy(new FluentTestRunnerAdapter());

        adapter.starting();

        adapter.finished();
    }

    @Test
    public void testStartingFinishWithName() {
        final FluentTestRunnerAdapter adapter = spy(new FluentTestRunnerAdapter(new DefaultSharedMutator()));

        adapter.starting("test");

        adapter.finished("test");
    }

    @Test
    public void testFailedWhenNoDriverAvailable() throws IOException {
        final FluentTestRunnerAdapter adapter = spy(new FluentTestRunnerAdapter());
        adapter.initFluent(driver);

        final Path tmpDirectory = Files.createTempDirectory("testFailedWhenNoDriverAvailable");
        adapter.getConfiguration().setScreenshotPath(tmpDirectory.toFile().getPath());
        adapter.getConfiguration().setHtmlDumpPath(tmpDirectory.toFile().getPath());

        adapter.getConfiguration().setScreenshotMode(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
        adapter.getConfiguration().setHtmlDumpMode(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);

        when(adapter.isFluentControlAvailable()).thenReturn(false);

        try {
            adapter.failed();
        } finally {
            FileUtils.deleteDirectory(tmpDirectory.toFile());
        }

        verify(adapter).failed(isNull(Throwable.class), eq(adapter.getClass()), anyString());

        verify(adapter, never()).takeScreenShot();
        verify(adapter, never()).takeScreenShot(anyString());
        verify(adapter, never()).takeHtmlDump();
        verify(adapter, never()).takeHtmlDump(anyString());
    }

    @Test
    public void testFailedWhenDriverAvailable() throws IOException {
        final FluentTestRunnerAdapter adapter = spy(new FluentTestRunnerAdapter());
        adapter.initFluent(driver);

        final Path tmpDirectory = Files.createTempDirectory("testFailedWhenDriverAvailable");
        adapter.getConfiguration().setScreenshotPath(tmpDirectory.toFile().getPath());
        adapter.getConfiguration().setHtmlDumpPath(tmpDirectory.toFile().getPath());

        final Path tempFile = Files.createTempFile("testFailedWhenDriverAvailable", "");
        when(driver.getScreenshotAs(OutputType.FILE)).thenReturn(tempFile.toFile());
        tempFile.toFile().deleteOnExit();

        adapter.getConfiguration().setScreenshotMode(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
        adapter.getConfiguration().setHtmlDumpMode(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
        when(adapter.canTakeScreenShot()).thenReturn(true);

        when(adapter.isFluentControlAvailable()).thenReturn(true);

        try {
            adapter.failed("test");
        } finally {
            FileUtils.deleteDirectory(tmpDirectory.toFile());
        }
        verify(adapter).failed(isNull(Throwable.class), eq(adapter.getClass()), anyString());

        verify(adapter, never()).takeScreenShot();
        verify(adapter).takeScreenShot(anyString());
        verify(adapter, never()).takeHtmlDump();
        verify(adapter).takeHtmlDump(anyString());
    }
}
