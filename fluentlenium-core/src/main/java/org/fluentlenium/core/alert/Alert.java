package org.fluentlenium.core.alert;

import lombok.experimental.Delegate;
import org.openqa.selenium.WebDriver;

/**
 * Util Class for manage alert
 */
public class Alert implements org.openqa.selenium.Alert {

    private final WebDriver webDriver;

    @Delegate
    private org.openqa.selenium.Alert getSeleniumAlert() { // NOPMD UnusedPrivateMethod
        return webDriver.switchTo().alert();
    }

    public Alert(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void prompt(String text) {
        sendKeys(text);
        accept();
    }

    /**
     * Switch to an alert box.
     *
     * @return this object reference to chain calls.
     * @throws org.openqa.selenium.NoAlertPresentException if there is currently no alert box.
     */
    public org.openqa.selenium.Alert switchTo() {
        return webDriver.switchTo().alert();
    }

}
