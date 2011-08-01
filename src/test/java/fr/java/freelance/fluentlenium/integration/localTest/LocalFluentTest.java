package fr.java.freelance.fluentlenium.integration.localTest;

import fr.java.freelance.fluentlenium.core.test.FluentTest;
import fr.java.freelance.fluentlenium.integration.JettyServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

//TODO : Problem here - 1 instance by test when 1 instance for test suite is sufficient ...
public class LocalFluentTest extends FluentTest {
    protected static final String DEFAULT_URL = "http://localhost:8585";
    public static JettyServer server = new JettyServer();

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver();

    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        server.start();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        server.stop();
    }

}
