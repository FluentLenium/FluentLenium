package fr.javafreelance.integration.localTest;

import fr.javafreelance.fluentlenium.core.test.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

//TODO : Problem here - 1 instance by test when 1 instance for test suite is sufficient ...
public abstract class LocalFluentCase extends FluentTest {
    //protected static final String DEFAULT_URL = "http://java-freelance.fr:8585/static/";
    protected static final String DEFAULT_URL = "http://localhost:8787/static/";

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver(true);
    }
}

