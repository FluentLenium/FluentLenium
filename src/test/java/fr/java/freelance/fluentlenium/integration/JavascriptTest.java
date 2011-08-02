package fr.java.freelance.fluentlenium.integration;


import fr.java.freelance.fluentlenium.integration.localTest.LocalFluentCase;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class JavascriptTest extends LocalFluentCase {

    @Test
    public void checkTextParam() {
        goTo(DEFAULT_URL + "javascript.html");
        assertThat(find("#default").first().getText()).isEqualTo("unchanged");
        executeScript("change();");
        assertThat(find("#default").first().getText()).isEqualTo("changed");

    }

}
