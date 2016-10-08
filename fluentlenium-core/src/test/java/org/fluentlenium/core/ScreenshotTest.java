package org.fluentlenium.core;

import org.fluentlenium.adapter.FluentAdapter;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ScreenshotTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private FluentAdapter adapter;

    @Test(expected = WebDriverException.class)
    public void whenBrowserDoesntAcceptScreenshotThenCustomError() {
        adapter = new FluentAdapter();
        adapter.initFluent(new CustomWebDriverNoScreenshot());
        adapter.takeScreenShot();
    }

    @Test
    public void whenBrowserDoesAcceptScreenshotThenNoException() throws IOException {
        adapter = new FluentAdapter();
        adapter.initFluent(new CustomWebDriverScreenshot());
        adapter.takeScreenShot(folder.newFile("test.jpg").getAbsolutePath());
    }

    @After
    public void release() {
        adapter.releaseFluent();
    }
}

class CustomWebDriverNoScreenshot implements WebDriver {

    public void get(final String url) {
        //Do nothing.
    }

    public String getCurrentUrl() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public List<WebElement> findElements(final By by) {
        return null;
    }

    public WebElement findElement(final By by) {
        return null;
    }

    public String getPageSource() {
        return null;
    }

    public void close() {
        //Do nothing.
    }

    public void quit() {
        //Do nothing.
    }

    public Set<String> getWindowHandles() {
        return null;
    }

    public String getWindowHandle() {
        return null;
    }

    public TargetLocator switchTo() {
        return null;
    }

    public Navigation navigate() {
        return null;
    }

    public Options manage() {
        return null;
    }
}

class CustomWebDriverScreenshot implements WebDriver, TakesScreenshot {

    public void get(final String url) {
        // Do nothing.
    }

    public String getCurrentUrl() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public List<WebElement> findElements(final By by) {
        return null;
    }

    public WebElement findElement(final By by) {
        return null;
    }

    public String getPageSource() {
        return null;
    }

    public void close() {
        // Do nothing.
    }

    public void quit() {
        // Do nothing.
    }

    public Set<String> getWindowHandles() {
        return null;
    }

    public String getWindowHandle() {
        return null;
    }

    public TargetLocator switchTo() {
        return null;
    }

    public Navigation navigate() {
        return null;
    }

    public Options manage() {
        return null;
    }

    public <X> X getScreenshotAs(final OutputType<X> xOutputType) throws WebDriverException {
        return xOutputType.convertFromBase64Png("test");
    }
}
