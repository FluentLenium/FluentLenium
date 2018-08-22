package org.fluentlenium.adapter.cucumber.integration.noinheritance.steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage2;
import org.fluentlenium.core.annotation.Page;

public class SimpleScenarioMultiNoInheritanceStep2 {

    @Page
    protected LocalPage page;

    @Page
    protected LocalPage2 page2;


    @When(value = "scenario multi2 I click on next page")
    public void step2() {
        page.$("a#linkToPage2").click();
    }

    @Then(value = "scenario multi2 I am on the second page")
    public void step3() {
        page2.isAt();
    }

}
