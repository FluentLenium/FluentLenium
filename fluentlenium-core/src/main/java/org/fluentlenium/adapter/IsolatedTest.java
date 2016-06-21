package org.fluentlenium.adapter;

import org.openqa.selenium.WebDriver;

/**
 * Instead of extending FluentTest, you can instantiate this class directly.
 * <p>
 * If you want to test concurrency, or if you need for any reason to not use JUnit nor TestNG, you may use this class.
 */
public class IsolatedTest extends FluentAdapter {

    public IsolatedTest() {
        initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
        init();
    }

    public IsolatedTest(WebDriver webDriver) {
        initFluent(webDriver).withDefaultUrl(getDefaultBaseUrl());
        init();
    }

}
