package org.fluentlenium.adapter.cucumber.integration.configuration.annotation.steps;

import cucumber.api.java.en.Given;

public class SimpleFeatureMultiStep1 extends BaseTest {

    @Given(value = "feature multi1 I am on the first page")
    public void step1() {
        goTo(page);
    }
}
