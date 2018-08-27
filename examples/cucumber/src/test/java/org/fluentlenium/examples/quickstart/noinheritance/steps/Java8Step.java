package org.fluentlenium.adapter.cucumber.custom.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.fluentlenium.adapter.cucumber.custom.page.HomePage;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.core.annotation.Page;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.adapter.cucumber.FluentCucumberTestContainer.FLUENT_TEST;

public class Java8Step implements En {

    @Page
    HomePage page;

    public Java8Step() {

        Given("Visit duckduckgo"), () -> goTo(page));

        When("I search FluentLenium", () -> {
            page.el("#search_form_input_homepage").fill().with("FluentLenium");
            page.el("#search_button_homepage").submit();
        });

        Then("Title contains FluentLenium", () -> assertThat(page.window().title()).contains("FluentLenium"));


        Before(Scenario scenario) -> FLUENT_TEST.instance().before(scenario));

        After(Scenario scenario) -> FLUENT_TEST.instance().after(scenario));
    }
}
