package org.fluentlenium.core;

import static java.util.Objects.requireNonNull;

import org.fluentlenium.configuration.Configuration;
import org.fluentlenium.utils.ImageUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Persists a screenshot to a target file.
 */
public class FluentDriverScreenshotPersister {

    private static final Logger LOGGER  = LoggerFactory.getLogger(FluentDriverScreenshotPersister.class);

    private final Configuration configuration;
    private final WebDriver driver;

    public FluentDriverScreenshotPersister(Configuration configuration, WebDriver driver) {
        this.configuration = requireNonNull(configuration);
        this.driver = driver;
    }

    /**
     * Persists a screenshot to the argument target file using the screenshot path from {@link Configuration}.
     * <p>
     * If there is no screenshot path set in the configuration, the file will be the argument file name,
     * otherwise the argument file name will be concatenated to the screenshot path to create the destination file.
     *
     * @param fileName the target file to save the screenshot to
     * @throws RuntimeException when an error occurs during taking the screenshot
     */
    public void persistScreenshot(String fileName) {
        try {
            File destFile;
            if (configuration.getScreenshotPath() == null) {
                destFile = new File(fileName);
            } else {
                destFile = Paths.get(configuration.getScreenshotPath(), fileName).toFile();
            }
            FileUtils.writeByteArrayToFile(destFile, prepareScreenshot());
            LOGGER.info("Created screenshot at: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Error when taking the screenshot", e);
        }
    }

    private byte[] prepareScreenshot() {
        byte[] screenshot;
        try {
            screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (UnhandledAlertException uae) {
            screenshot = new ImageUtils(driver).handleAlertAndTakeScreenshot();
        }
        return screenshot;
    }
}
