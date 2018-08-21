package org.fluentlenium.adapter.cucumber.integration.driverperscenario.steps;

import cucumber.api.java.en.Given;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.core.annotation.Page;

public class SimpleScenarioMultiStep1 extends FluentCucumberTest {

    @Page
    protected LocalPage page;

    @Given(value = "scenario multi1 I am on the first page")
    public void step1() {
        page.go();
    }
}
