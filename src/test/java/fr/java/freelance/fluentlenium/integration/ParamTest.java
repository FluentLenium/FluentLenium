package fr.java.freelance.fluentlenium.integration;

import fr.java.freelance.fluentlenium.integration.localTest.LocalFluentCase;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


public class ParamTest extends LocalFluentCase {


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

    @Test
    public void checkPageSource() {
        goTo(DEFAULT_URL);
        assertThat(pageSource()).contains("<body name=\"body\" style=\"\">");
    }
}
