package org.fluentlenium.adapter.cucumber.integration.tests.nodriver.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

@FluentConfiguration(webDriver = "htmlunit")
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

    @Before({"@fluent"})
    public void beforeScenario(Scenario scenario) {
        before(scenario);
    }

    @After({"@fluent"})
    public void afterScenario(Scenario scenario) {
        after(scenario);
    }
}

class TestPage extends FluentPage {

}

