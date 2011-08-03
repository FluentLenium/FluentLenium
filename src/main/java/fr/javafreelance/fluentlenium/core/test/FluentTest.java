package fr.javafreelance.fluentlenium.core.test;

import fr.javafreelance.fluentlenium.core.Fluent;
import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    public WebDriverWait getDefaultWait() {
        return new WebDriverWait(getDefaultDriver(), 5);
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

        getDriver().quit();
    }


}
