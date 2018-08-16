package org.fluentlenium.adapter.cucumber.integration.factory.steps;

import cucumber.api.java.en.Given;

public class SimpleFeatureMultiStep1 extends BaseTest {

    @Given(value = "feature multi1 I am on the first page")
    public void step1() {
        page.go(page);
    }
}
