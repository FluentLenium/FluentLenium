package fr.java.freelance.fluentlenium.integration;

import fr.java.freelance.fluentlenium.integration.localTest.LocalFluentTest;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


public class ParamTest extends LocalFluentTest {


    @Test
    public void checkTitleParam() {
        goTo(DEFAULT_URL);
        assertThat(title()).contains("Selenium");
    }

    @Test
    public void checkUrlParam() {
        goTo(DEFAULT_URL);
        assertThat(url()).isEqualTo(DEFAULT_URL);
    }
}
