package fr.java.freelance.fluentlenium.integration.localTest;

import fr.java.freelance.fluentlenium.core.test.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class LocalFluentCase extends FluentTest {
    protected static final String DEFAULT_URL = "http://localhost:8585/static/";

    @Override
    public WebDriver getDefaultDriver() {
        return new FirefoxDriver();

    }
}

