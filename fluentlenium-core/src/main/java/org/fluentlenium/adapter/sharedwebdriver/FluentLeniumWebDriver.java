package org.fluentlenium.adapter.sharedwebdriver;

public interface FluentLeniumWebDriver {

    default void quitDriver(SharedWebDriver sharedWebDriver, SharedWebDriver testDriver) {
        if (testDriver == sharedWebDriver && testDriver.getDriver() != null) { // NOPMD CompareObjectsWithEquals
            testDriver.getDriver().quit();
        }
    }

    void quitDriver(SharedWebDriver sharedWebDriver);

    void addDriver(SharedWebDriver driver);
}
