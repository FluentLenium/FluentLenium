package fr.java.freelance.fluentlenium.filter.Examples;

import fr.java.freelance.fluentlenium.core.test.FluentTest;
import org.junit.Ignore;
import org.junit.Test;

import static fr.java.freelance.fluentlenium.core.FluentPage.go;
import static org.fest.assertions.Assertions.assertThat;

@Ignore
public class BuildWallLoginJqueryStyleTest extends FluentTest {
    LoginPage loginPage = new LoginPage(getDriver());

    @Test
    public void errorShouldBeThrownWhenMissingMandatoryFields() {
        go(loginPage);
        $("#user_username").text("toto");
        $("#user_firstname").text("tata");
        loginPage.submitForm();
        assertThat($("label").getTexts()).contains("This field is required.");
    }


    @Test
    public void errorShouldBeIfNonAcceptableFields() {
        goTo("http://buildwall.com/#!/signup");
        $("input").text("toto");
        $("#create-button").click();
        assertThat($("label[generated=true]")).hasSize(4);

    }
}
