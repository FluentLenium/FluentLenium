package org.fluentlenium.adapter.cucumber.integration.tests.getbean.steps;

import cucumber.api.java.en.Given;
import org.fluentlenium.adapter.cucumber.integration.tests.setbean.steps.BaseTest;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.core.annotation.Page;

public class MultiGetBeanStep1 extends BaseTest {
    @Page
    private LocalPage page;

    @Given(value = "scenario multi1 I am on the first page")
    public void step1() {
        goTo(page);
    }
}
