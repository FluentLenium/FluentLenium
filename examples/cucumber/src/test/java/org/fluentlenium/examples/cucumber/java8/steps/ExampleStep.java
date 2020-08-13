package org.fluentlenium.examples.cucumber.java8.steps;

import io.cucumber.java8.En;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.cucumber.java8.page.HomePage;

import static org.assertj.core.api.Assertions.assertThat;

@FluentConfiguration(webDriver = "chrome")
public class ExampleStep extends FluentCucumberTest implements En {

    @Page
    private HomePage page;

    public ExampleStep() {

        Given("Visit duckduckgo", () -> goTo(page));

        When("I search FluentLenium", () -> {
            el("#search_form_input_homepage").fill().with("FluentLenium");
            el("#search_button_homepage").submit();
        });

        Then("Title contains FluentLenium", () ->
                assertThat(window().title()).contains("FluentLenium"));

        Before(this::before);

        After(this::after);
    }
}
