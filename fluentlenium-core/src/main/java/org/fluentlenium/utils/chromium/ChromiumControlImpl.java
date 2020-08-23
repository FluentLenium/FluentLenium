package org.fluentlenium.utils.chromium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class ChromiumControlImpl implements ChromiumControl {

    private WebDriver driver;

    public ChromiumControlImpl(WebDriver driver) {
        this.driver = driver;
    }

    public final ChromiumApi getChromiumApi() {
        if (driver instanceof EventFiringWebDriver) {
            driver = ((EventFiringWebDriver) driver).getWrappedDriver();
        }

        RemoteWebDriver remoteWebDriver;
        try {
            remoteWebDriver = (RemoteWebDriver) driver;
        } catch (ClassCastException ex) {
            throw new ChromiumApiNotSupportedException("API supported only by Chrome and Edge");
        }
        return new ChromiumApi(remoteWebDriver);
    }

}
