package org.fluentlenium.adapter;

import org.fluentlenium.core.FluentDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FluentAdapter extends FluentDriver {

    public FluentAdapter(WebDriver webDriver) {
        super(webDriver);
    }

    public FluentAdapter() {
        super();
    }

    /**
     * Override this method to change the driver
     *
     * @return returns WebDriver which is set to FirefoxDriver by default - can be overwritten
     */
    public WebDriver getDefaultDriver() {
        return new FirefoxDriver();
    }

    /**
     * Override this method to set the base URL to use when using relative URLs
     *
     * @return The base URL, or null if relative URLs should be passed to the driver untouched
     */
    public String getDefaultBaseUrl() {
        return null;
    }
    
}
