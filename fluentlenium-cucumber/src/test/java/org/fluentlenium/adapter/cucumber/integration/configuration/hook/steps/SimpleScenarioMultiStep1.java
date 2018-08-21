package org.fluentlenium.adapter.cucumber.integration.configuration.hook.steps;

import cucumber.api.java.en.Given;

public class SimpleScenarioMultiStep1 extends BaseTest {

    @Given(value = "scenario multi1 I am on the first page")
    public void step1() {
        page.go();
    }
}
