package fr.java.freelance.fluentlenium.filter;

import fr.java.freelance.fluentlenium.filter.localTest.LocalFluentTest;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


public class ParamTest extends LocalFluentTest {

    private static final String DEFAULT_URL = "http://localhost:8585";

    @Test
    public void checkTitleParam() {
        goTo("http://localhost:8585");
        assertThat(title()).contains("Selenium");
    }

    @Test
    public void checkUrlParam() {
        goTo(DEFAULT_URL);
        assertThat(url()).isEqualTo("http://localhost:8585/");
    }
}
