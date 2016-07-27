package org.fluentlenium.core;


import org.fluentlenium.adapter.FluentAdapter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.openqa.selenium.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ScreenshotTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test(expected = WebDriverException.class)
    public void when_browser_doesnt_accept_screenshot_then_custom_error() {
        new FluentAdapter(new CustomWebDriverNoScreenshot()).takeScreenShot();
    }

    @Test
    public void when_browser_does_accept_screenshot_then_no_exception() throws IOException {
        new FluentAdapter(new CustomWebDriverScreenshot()).takeScreenShot(folder.newFile("test.jpg").getAbsolutePath());
    }
}

class CustomWebDriverNoScreenshot implements WebDriver {

    public void get(String s) {

    }

    public String getCurrentUrl() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public List<WebElement> findElements(By by) {
        return null;
    }

    public WebElement findElement(By by) {
        return null;
    }

    public String getPageSource() {
        return null;
    }

    public void close() {

    }

    public void quit() {

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

    public void get(String s) {

    }

    public String getCurrentUrl() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public List<WebElement> findElements(By by) {
        return null;
    }

    public WebElement findElement(By by) {
        return null;
    }

    public String getPageSource() {
        return null;
    }

    public void close() {

    }

    public void quit() {

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

    public <X> X getScreenshotAs(OutputType<X> xOutputType) throws WebDriverException {
        return xOutputType.convertFromBase64Png("test");
    }
}
