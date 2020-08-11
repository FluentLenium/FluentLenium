package org.fluentlenium.adapter.sharedwebdriver;

/**
 * Driver interface for {@link org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle} based
 * {@link SharedWebDriver} handling.
 */
public interface FluentLeniumDriver {

    default void quitDriver(SharedWebDriver sharedWebDriver, SharedWebDriver testDriver) {
        if (testDriver == sharedWebDriver && testDriver.getDriver() != null) { // NOPMD CompareObjectsWithEquals
            testDriver.getDriver().quit();
        }
    }

    /**
     * Quits the argument shared webdriver.
     *
     * @param sharedWebDriver the driver to quit
     */
    void quitDriver(SharedWebDriver sharedWebDriver);

    /**
     * Adds this driver to the collection of shared webdrivers according to the driver lifecycle.
     *
     * @param driver the driver to add
     */
    void addDriver(SharedWebDriver driver);
}
