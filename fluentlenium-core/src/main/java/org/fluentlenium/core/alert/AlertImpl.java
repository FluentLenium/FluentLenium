package org.fluentlenium.core.alert;

import org.openqa.selenium.WebDriver;

/**
 * Manage browser alert.
 */
public class AlertImpl implements Alert {

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

    public org.openqa.selenium.Alert getAlert() {
        return alert;
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

    public String getText() {
        return getAlert().getText();
    }

    public void accept() {
        getAlert().accept();
    }

    public void sendKeys(String keysToSend) {
        getAlert().sendKeys(keysToSend);
    }

    public void dismiss() {
        getAlert().dismiss();
    }
}
