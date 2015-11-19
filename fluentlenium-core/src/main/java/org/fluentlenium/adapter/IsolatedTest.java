package org.fluentlenium.adapter;

import org.fluentlenium.core.FluentAdapter;
import org.openqa.selenium.Beta;
import org.openqa.selenium.WebDriver;

@Beta
public class IsolatedTest extends FluentAdapter {


    public IsolatedTest() {
        initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
        initTest();
    }

    public IsolatedTest(WebDriver webDriver) {
        initFluent(webDriver).withDefaultUrl(getDefaultBaseUrl());
        initTest();
    }

    public void quit() {
        if (getDriver() != null) {
            super.quit();
        }
    }


}
