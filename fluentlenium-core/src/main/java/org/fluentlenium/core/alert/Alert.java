package org.fluentlenium.core.alert;

import lombok.experimental.Delegate;
import org.openqa.selenium.WebDriver;

/**
 * Manage browser alert.
 */
public class Alert implements org.openqa.selenium.Alert {

    private final WebDriver driver;

    @Delegate
    private org.openqa.selenium.Alert getSeleniumAlert() { // NOPMD UnusedPrivateMethod
        return driver.switchTo().alert();
    }

    /**
     * Creates a new alert object.
     *
     * @param driver selenium driver
     */
    public Alert(final WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Send input to the alert prompt.
     *
     * @param text test to send to the prompt
     */
    public void prompt(final String text) {
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
        return driver.switchTo().alert();
    }

}
