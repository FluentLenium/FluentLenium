package org.fluentlenium.adapter;

import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.FluentThread;
import org.fluentlenium.core.page.PageInitializerException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FluentAdapter extends Fluent {

    public FluentAdapter(WebDriver webDriver) {
        super(webDriver);
        FluentThread.set(this);
    }

    public FluentAdapter() {
        super();
        FluentThread.set(this);
    }

    protected void init() {
        try {
            pageInitializer.initContainer(this);
        } catch (ClassNotFoundException e) {
            throw new PageInitializerException("Class not found", e);
        } catch (IllegalAccessException e) {
            throw new PageInitializerException("IllegalAccessException", e);
        }
        getDefaultConfig();
    }

    protected void close() {
        pageInitializer.release();
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


    /**
     * Override this method to set some config options on the driver. For example withDefaultSearchWait and withDefaultPageWait
     * Remember that you can access to the WebDriver object using this.getDriver().
     */
    public void getDefaultConfig() {
    }
}
