package org.fluentlenium.adapter;

/**
 * Instead of extending FluentTest, you can instantiate this class directly.
 * <p>
 * If you want to test concurrency, or if you need for any reason to not use JUnit nor TestNG,
 * you may use this class.
 * <p>
 * You should call {@link #quit()} manually to close the underlying webdriver.
 */
public class IsolatedTest extends FluentAdapter {

    /**
     * Creates a new isolated test.
     */
    public IsolatedTest() {
        initFluent(newWebDriver());
    }

    /**
     * Quite the test.
     */
    public void quit() {
        if (getDriver() != null) {
            getDriver().quit();
        }
        releaseFluent();
    }
}
