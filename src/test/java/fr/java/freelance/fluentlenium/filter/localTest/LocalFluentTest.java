package fr.java.freelance.fluentlenium.filter.localTest;

import fr.java.freelance.fluentlenium.JettyServer;
import fr.java.freelance.fluentlenium.core.test.FluentTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;

//TODO : Problem here - 1 instance by test when 1 instance for test suite is sufficient ...
public class LocalFluentTest extends FluentTest {
    protected static final String DEFAULT_URL = "http://localhost:8585";
    public static JettyServer server = new JettyServer();

    @BeforeClass
    public static void beforeClass() throws Exception {
        server.start();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        server.stop();
    }

}
