package org.fluentlenium.adapter;

import org.openqa.selenium.WebDriver;

/**
 * Instead of extending FluentTest, you can instantiate this class directly.
 * <p>
 * If you want to test concurrency, or if you need for any reason to not use JUnit nor TestNG, you may use this class.
 * <p>
 * You should call {@link #releaseFluent()} manually to close the underlying webdriver.
 */
public class IsolatedTest extends FluentAdapter {

    public IsolatedTest() {
        initFluent(newWebDriver());
    }

    @Deprecated
    public IsolatedTest(WebDriver webDriver) {
        initFluent(webDriver);
    }

    @Deprecated
    public IsolatedTest(DriverContainer driverContainer) {
        super(driverContainer);
        initFluent(newWebDriver());
    }

    @Deprecated
    public IsolatedTest(DriverContainer driverContainer, WebDriver webDriver) {
        super(driverContainer);
        initFluent(webDriver);
    }

}
