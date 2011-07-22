package fr.java.freelance.fluentlenium.filter.Examples;

import fr.java.freelance.fluentlenium.core.test.FluentTest;
import org.junit.Ignore;
import org.junit.Test;

import static fr.java.freelance.fluentlenium.core.FluentPage.assertOn;
import static fr.java.freelance.fluentlenium.core.FluentPage.go;
import static org.fest.assertions.Assertions.assertThat;

@Ignore
public class BuildWallLoginBddStyleTest extends FluentTest {
    LoginPage loginPage = new LoginPage(getDriver());

    @Test
    public void errorShouldBeThrownWhenMissingMandatoryFields() {
        go(loginPage);
        assertAt(loginPage);
        fill("#user").with("toto");
        click("#create-button");
        assertThat(text("label")).contains("This field is required.");
    }

    @Test
    public void errorShouldBeIfNonAcceptableFields() {
        goTo("http://buildwall.com/#!/signup");
        fill("input").with("toto");
        click("#create-button");
        assertThat($("label[generated=true]")).hasSize(4);

    }
}
