package io.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.getbean.steps;

import io.cucumber.java.en.Given;
import io.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import io.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.setbean.steps.BaseTest;
import io.fluentlenium.core.annotation.Page;

public class MultiGetBeanStep1 extends BaseTest {
    @Page
    private LocalPage page;

    @Given(value = "scenario multi1 I am on the first page")
    public void step1() {
        goTo(page);
    }
}
