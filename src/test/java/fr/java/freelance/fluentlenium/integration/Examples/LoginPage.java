package fr.java.freelance.fluentlenium.integration.Examples;

import fr.java.freelance.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

import static org.fest.assertions.Assertions.assertThat;

public class LoginPage extends FluentPage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public String getUrl() {
        return "http://buildwall.com/#!/signup";
    }

    public void isAt() {
        assertThat($("title").first().getText()).isEqualTo("Buildwall");
    }

    public void submitForm() {
        $("#create-button").click();
    }


}