package fr.javafreelance.integration.localTest;

import fr.javafreelance.fluentlenium.core.test.FluentTest;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

//TODO : Problem here - 1 instance by test when 1 instance for test suite is sufficient ...
public abstract class LocalFluentCase extends FluentTest {
    protected static final String DEFAULT_URL = "http://localhost:8585/static/";

    @Override
    public WebDriver getDefaultDriver() {
        DesiredCapabilities capabillities = new DesiredCapabilities(
                "firefox", "3.6.", Platform.WINDOWS);
        capabillities.setCapability("name", "Test of FluentLenium");
        WebDriver driver = null;
        try {
            driver = new RemoteWebDriver(
                    new URL("http://fluentlenium:8906940f-5638-4c29-beb6-c331df039f48@ondemand.saucelabs.com:80/wd/hub"),
                    capabillities);
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return driver;
    }
}

