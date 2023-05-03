package io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.getbean.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import io.fluentlenium.adapter.cucumber.integration.page.LocalPage2;
import io.fluentlenium.core.annotation.Page;

public class SingleGetBeanStep extends BaseTest {

    @Page
    protected LocalPage page;

    @Page
    protected LocalPage2 page2;

    @Given(value = "scenario I am on the first page")
    public void step1() {
        goTo(page);
    }

    @When(value = "scenario I click on next page")
    public void step2() {
        page.clickLink();
    }

    @Then(value = "scenario I am on the second page")
    public void step3() {
        page2.isAt();
    }
}
