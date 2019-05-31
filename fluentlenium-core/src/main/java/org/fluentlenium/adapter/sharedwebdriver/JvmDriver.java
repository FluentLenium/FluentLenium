package org.fluentlenium.adapter.sharedwebdriver;

public class JvmDriver implements FluentLeniumDriver {

    private SharedWebDriver jvmDriver;

    public SharedWebDriver getDriver() {
        return jvmDriver;
    }

    @Override
    public void quitDriver(SharedWebDriver driver) {
        if (jvmDriver == driver) { // NOPMD CompareObjectsWithEquals
            if (jvmDriver.getDriver() != null) {
                jvmDriver.getDriver().quit();
            }
            jvmDriver = null;
        }
    }

    @Override
    public void addDriver(SharedWebDriver driver) {
        this.jvmDriver = driver;
    }
}
