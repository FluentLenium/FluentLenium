package org.fluentlenium.examples.cucumber.pageobject.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.cucumber.pageobject.page.HomePage;

import static org.assertj.core.api.Assertions.assertThat;

@FluentConfiguration(webDriver = "chrome")
public class PageObjectStep extends FluentCucumberTest {

    @Page
    private HomePage page;

    @Given(value = "Visit duckduckgo")
    public void step1() {
        goTo(page);
    }

    @When(value = "I search FluentLenium")
    public void step2() {
        page.find("FluentLenium");
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
