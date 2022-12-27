package io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.multiinheritance.steps;

import io.cucumber.java.en.Given;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.adapter.cucumber.FluentCucumberTest;
import io.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import io.fluentlenium.core.annotation.Page;

public class MultiGetBeanStep1 extends FluentCucumberTest {
    @Page
    private LocalPage page;

    @Given(value = "scenario multi1 I am on the first page")
    public void step1() {
        goTo(page);
    }
}
