package org.fluentlenium.core.alert;

import org.openqa.selenium.WebDriver;

/**
 * Manages a browser alert.
 * <p>
 * Wraps a Selenium {@link org.openqa.selenium.Alert} instance but an instance of this type
 * is created successfully only when there is an actual alert present.
 */
public class AlertImpl implements Alert {

    private final org.openqa.selenium.Alert alert;  // NOPMD UnusedPrivateMethod

    /**
     * Creates a new alert object.
     * <p>
     * Uses the underlying Alert object from the provided driver instance.
     * <p>
     * If there is no alert present in the provided driver, it throws an {@link org.openqa.selenium.NoAlertPresentException}
     * and the instantiation is interrupted.
     *
     * @param driver selenium driver
     */
    public AlertImpl(WebDriver driver) {
        alert = driver.switchTo().alert();
    }

    /**
     * Creates a new alert object using the provided Alert instance.
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

    /**
     * {@inheritDoc}
     * <p>
     * The return value is hardcoded to true, since this object is instantiated successfully only when
     * there is an alert present.
     */
    @Override
    public boolean present() {
        return true;
    }

    @Override
    public String getText() {
        return getAlert().getText();
    }

    @Override
    public void accept() {
        getAlert().accept();
    }

    @Override
    public void sendKeys(String keysToSend) {
        getAlert().sendKeys(keysToSend);
    }

    @Override
    public void dismiss() {
        getAlert().dismiss();
    }
}
