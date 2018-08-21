package org.fluentlenium.adapter.cucumber.integration.driverperfeature.steps;

import cucumber.api.java.en.Given;
import org.fluentlenium.adapter.cucumber.integration.driverperfeature.BaseTest;

public class SimpleFeatureMultiStep1 extends BaseTest {

    @Given(value = "feature multi1 I am on the first page")
    public void step1() {
        goTo(page);
    }
}
