package org.fluentlenium.adapter.cucumber.integration.configuration.noinheritance.steps;

import cucumber.api.java.en.Given;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.core.annotation.Page;

public class SimpleFeatureMultiStep1 {

    @Page
    protected LocalPage page;

    @Given(value = "feature multi1 I am on the first page")
    public void step1() {
        page.go();
    }
}
