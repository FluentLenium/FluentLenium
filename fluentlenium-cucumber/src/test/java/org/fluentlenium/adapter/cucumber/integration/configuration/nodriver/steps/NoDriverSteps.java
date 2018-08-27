package org.fluentlenium.adapter.cucumber.integration.configuration.nodriver.steps;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.adapter.cucumber.FluentCucumberTestContainer.FLUENT_TEST;

public class NoDriverSteps {

    private WebDriver driver;

    private String state;

    @When("I get WebDriver current state")
    public void checkWebDriver() {
        driver = FLUENT_TEST.instance().getDriver();
        System.out.println(driver);
        assertThat(driver).isNull();
    }

    @Then("it should be null")
    public void driverShouldBeNull() {
        System.out.println(driver);
        assertThat(driver).isNull();
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
}

