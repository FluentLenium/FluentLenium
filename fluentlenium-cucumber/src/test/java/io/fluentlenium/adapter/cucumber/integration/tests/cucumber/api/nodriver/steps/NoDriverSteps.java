package io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.nodriver.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.fluentlenium.core.FluentPage;import io.fluentlenium.core.annotation.Page;import io.fluentlenium.adapter.cucumber.FluentCucumberTest;
import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class NoDriverSteps extends FluentCucumberTest {

    @Page
    private TestPage testPage;

    private WebDriver driver;

    private String state;

    @When("I get WebDriver current state")
    public void checkWebDriver() {
        driver = getDriver();
    }

    @Then("it should be null")
    public void driverShouldBeNull() {
        assertThat(getDriver()).isNull();
    }

    @Then("it should not be null with tag")
    public void driverShouldNotBeNull() {
        assertThat(getDriver()).isNotNull();
    }

    @And("it should be created instance of @Page")
    public void pageShouldBeCreated() {
        assertThat(testPage).isNotNull();
    }

    @Given("I have important string")
    public void haveImportantString() {
        state = "Important";
    }

    @When("I change content of string")
    public void changeContentOfString() {
        state = "Fluent";
    }

    @Then("it should be correctly changed")
    public void shouldBeChanged() {
        assertThat(state)
                .isNotNull()
                .isNotBlank()
                .doesNotContain("Important")
                .contains("Fluent");
    }

    @Before("@fluent")
    public void beforeScenario(Scenario scenario) {
        before(scenario);
    }

    @After("@fluent")
    public void afterScenario(Scenario scenario) {
        after(scenario);
    }
}

class TestPage extends FluentPage {

}

