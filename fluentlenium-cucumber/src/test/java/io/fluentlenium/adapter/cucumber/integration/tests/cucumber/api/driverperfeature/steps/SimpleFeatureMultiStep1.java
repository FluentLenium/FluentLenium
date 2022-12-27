package io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.driverperfeature.steps;

import io.cucumber.java.en.Given;
import io.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import io.fluentlenium.core.annotation.Page;

public class SimpleFeatureMultiStep1 extends BaseTest {

    @Page
    protected LocalPage page;

    @Given(value = "feature multi1 I am on the first page")
    public void step1() {
        goTo(page);
    }
}
