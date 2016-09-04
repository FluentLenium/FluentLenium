package org.fluentlenium.core.alert;

import lombok.experimental.Delegate;
import org.openqa.selenium.WebDriver;

/**
 * Util Class for manage alert
 */
public class Alert implements org.openqa.selenium.Alert {

    private WebDriver webDriver;

    @Delegate
    private org.openqa.selenium.Alert getSeleniumAlert() {
        return webDriver.switchTo().alert();
    }

    public Alert(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void prompt(String s) {
        sendKeys(s);
        accept();
    }

    /**
     * Switch to an alert box.
     *
     * @throws org.openqa.selenium.NoAlertPresentException if there is currently no alert box.
     */
    public org.openqa.selenium.Alert switchTo() {
        return webDriver.switchTo().alert();
    }

}
