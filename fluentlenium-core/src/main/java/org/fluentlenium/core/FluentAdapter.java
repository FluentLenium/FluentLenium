package org.fluentlenium.core;

import org.fluentlenium.core.page.PageInitializerException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FluentAdapter extends Fluent {

    public FluentAdapter(WebDriver webDriver) {
        super(webDriver);
    }

    public FluentAdapter() {
        super();
    }

    protected void initTest() {
        try {
            pageInitializer.initContainer(this);
        } catch (ClassNotFoundException e) {
            throw new PageInitializerException("Class not found", e);
        } catch (IllegalAccessException e) {
            throw new PageInitializerException("IllegalAccessException", e);
        }
    }

    protected void cleanUp() {
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

    /**
     * @deprecated use FluentPage.isAt() instead.
     *
     */
    @Deprecated
    public static void assertAt(FluentPage fluent) {
        fluent.isAt();
    }
}
