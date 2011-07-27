package fr.java.freelance.fluentlenium.core.test;

import fr.java.freelance.fluentlenium.core.Fluent;
import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * All Junit Test should extends this class. It provides default parameters.
 */
public abstract class FluentTest extends Fluent {

    public FluentTest() {
        super();
        this.setDriver(getDefaultDriver());
    }

    /**
     * Override this method to change the driver
     *
     * @return
     */
    public WebDriver getDefaultDriver() {
        return new FirefoxDriver();
    }

    /**
     * Open the url page
     *
     * @param url
     */
    public void goTo(String url) {
        getDriver().get(url);
    }


    @After
    public void after() {
        getDriver().close();
    }


}
