package io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.driverperfeature.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import io.fluentlenium.core.annotation.Page;

public class SimpleFeatureMultiStep2 extends BaseTest {

    @Page
    protected LocalPage page2;

    @When(value = "feature multi2 I click on next page")
    public void step2() {
        el("a#linkToPage2").click();
    }

    @Then(value = "feature multi2 I am on the second page")
    public void step3() {
        page2.isAt();
    }
}
