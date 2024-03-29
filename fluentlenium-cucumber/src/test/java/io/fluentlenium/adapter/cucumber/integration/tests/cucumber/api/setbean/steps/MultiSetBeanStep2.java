package io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.setbean.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import io.fluentlenium.adapter.cucumber.integration.page.LocalPage2;
import io.fluentlenium.core.annotation.Page;

public class MultiSetBeanStep2 extends BaseTest {

    @Page
    protected LocalPage page;
    @Page
    protected LocalPage2 page2;

    @When(value = "scenario multi2 I click on next page")
    public void step2() {
        page.clickLink();
    }

    @Then(value = "scenario multi2 I am on the second page")
    public void step3() {
        page2.isAt();
    }
}
