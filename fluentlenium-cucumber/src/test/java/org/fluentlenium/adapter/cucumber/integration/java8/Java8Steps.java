package org.fluentlenium.adapter.cucumber.integration.java8;

import cucumber.api.Scenario;
import cucumber.api.java8.En;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.annotation.Page;

import static org.fluentlenium.adapter.cucumber.FluentCucumberTestContainer.FLUENT_TEST;

public class Java8Steps implements En {

    @Page
    protected LocalPage page;

    @Page
    protected LocalPage page2;

    public Java8Steps() {

        Given("scenario I am on the first page", () ->
                page.go());


        When("scenario I click on next page", () ->
                page.$("a#linkToPage2").click());


        Then("scenario I am on the second page", () ->
                page2.isAt());

        Before((Scenario scenario) -> FLUENT_TEST.instance().before(scenario));

        After((Scenario scenario) -> FLUENT_TEST.instance().after(scenario));
    }
}
