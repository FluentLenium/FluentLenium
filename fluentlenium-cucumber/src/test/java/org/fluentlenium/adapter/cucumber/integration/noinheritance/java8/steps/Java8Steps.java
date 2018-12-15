package org.fluentlenium.adapter.cucumber.integration.noinheritance.java8.steps;

import cucumber.api.java8.En;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.core.annotation.Page;

public class Java8Steps implements En {

    @Page
    protected LocalPage page;

    @Page
    protected LocalPage page2;

    public Java8Steps() {

        Given("scenario I am on the first page", () -> page.go());

        When("scenario I click on next page", () -> page.clickLink());

        Then("scenario I am on the second page", () -> page2.isAt());
    }
}
