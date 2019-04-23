package org.fluentlenium.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.fluentlenium.configuration.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

/**
 * Unit test for {@link FluentDriverScreenshotPersister}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FluentDriverScreenshotPersisterTest {

    @Mock
    private Configuration configuration;
    @Mock(extraInterfaces = TakesScreenshot.class)
    private WebDriver webDriver;

    private FluentDriverScreenshotPersister screenshotPersister;
    private File destinationFile;

    @Before
    public void setup() {
        screenshotPersister = new FluentDriverScreenshotPersister(configuration, webDriver);
    }

    @After
    public void tearDown() {
        destinationFile.delete();
    }

    @Test
    public void shouldCreateScreenshotFromDriverWithNoConfiguration() throws IOException {
        mockScreenshotFromWebDriver();
        initializeDestinationFile();

        screenshotPersister.persistScreenshot(destinationFile.getAbsolutePath());

        assertThat(destinationFile).exists();
    }

    @Test
    public void shouldCreateScreenshotFromDriverWithConfiguration() throws IOException {
        mockScreenshotFromWebDriver();
        initializeDestinationFile();
        when(configuration.getScreenshotPath()).thenReturn(destinationFile.getParent());

        screenshotPersister.persistScreenshot(destinationFile.getName());

        assertThat(destinationFile).exists();
    }

    private void mockScreenshotFromWebDriver() {
        byte[] screenshot = {1, 2};
        when(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES)).thenReturn(screenshot);
    }

    private void initializeDestinationFile() throws IOException {
        destinationFile = File.createTempFile("screenshot.png", "");
        destinationFile.delete();
    }
}
