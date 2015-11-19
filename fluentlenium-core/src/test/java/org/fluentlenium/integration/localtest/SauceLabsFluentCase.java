package org.fluentlenium.integration.localtest;

import org.fluentlenium.adapter.FluentTest;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class SauceLabsFluentCase extends FluentTest {
    protected static final String DEFAULT_URL = "http://java-freelance.fr:8585/static/";
    public static final String JAVASCRIPT_URL = "http://localhost:8787/static/javascript.html";


    @Override
    public WebDriver getDefaultDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities(
                "firefox", "3.6.", Platform.WINDOWS);
        capabilities.setCapability("name", "Test of FluentLenium");
        try {
            return new RemoteWebDriver(
                    new URL("http://fluentlenium:8906940f-5638-4c29-beb6-c331df039f48@ondemand.saucelabs.com:80/wd/hub"),
                    capabilities);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}

