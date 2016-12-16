package org.fluentlenium.core.alert;

import lombok.experimental.Delegate;
import org.openqa.selenium.WebDriver;

/**
 * Manage browser alert.
 */
public class AlertImpl implements Alert {

    @Delegate
    private final org.openqa.selenium.Alert alert;  // NOPMD UnusedPrivateMethod

    /**
     * Creates a new alert object.
     *
     * @param driver selenium driver
     */
    public AlertImpl(WebDriver driver) {
        alert = driver.switchTo().alert();
    }

    /**
     * Creates a new alert object.
     *
     * @param alert selenium alert
     */
    public AlertImpl(org.openqa.selenium.Alert alert) {
        this.alert = alert;
    }

    @Override
    public void prompt(String text) {
        sendKeys(text);
        accept();
    }

    @Override
    public boolean present() {
        return true;
    }
}
