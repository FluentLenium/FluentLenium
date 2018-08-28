package org.fluentlenium.examples.cucumber.update.steps;

import cucumber.api.Scenario;
import cucumber.api.java8.En;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.cucumber.page.HomePage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.adapter.cucumber.FluentCucumberTestContainer.FLUENT_TEST;

public class ExampleStep implements En {

    @Page
    private HomePage page;

    public ExampleStep() {

        Given("Visit duckduckgo", () -> page.go());

        When("I search FluentLenium", () -> {
            page.el("#search_form_input_homepage").fill().with("FluentLenium");
            page.el("#search_button_homepage").submit();
        });

        Then("Title contains FluentLenium", () -> assertThat(page.window().title()).contains("FluentLenium"));

        Before((Scenario scenario) -> FLUENT_TEST.instance().before(scenario));

        After((Scenario scenario) -> FLUENT_TEST.instance().after(scenario));
    }
}
