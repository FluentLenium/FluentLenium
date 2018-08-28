package org.fluentlenium.examples.cucumber.classic.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.examples.cucumber.page.HomePage;

import static org.assertj.core.api.Assertions.assertThat;

@FluentConfiguration(webDriver = "chrome")
public class BasicStep extends FluentCucumberTest {

    private HomePage page;

    @Given(value = "Visit duckduckgo")
    public void step1() {
        page = newInstance(HomePage.class);
        goTo(page);
    }

    @When(value = "I search FluentLenium")
    public void step2() {
        el("#search_form_input_homepage").fill().with("FluentLenium");
        el("#search_button_homepage").submit();
    }

    @Then(value = "Title contains FluentLenium")
    public void step3() {
        assertThat(page.window().title()).contains("FluentLenium");
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        this.before(scenario);
    }

    @After
    public void afterScenario(Scenario scenario) {
        this.after(scenario);
    }
}
