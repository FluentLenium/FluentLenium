package org.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.multiinheritance.steps;

import io.cucumber.java.en.Given;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.core.annotation.Page;

public class MultiGetBeanStep1 extends FluentCucumberTest {
    @Page
    private LocalPage page;

    @Given(value = "scenario multi1 I am on the first page")
    public void step1() {
        goTo(page);
    }
}
