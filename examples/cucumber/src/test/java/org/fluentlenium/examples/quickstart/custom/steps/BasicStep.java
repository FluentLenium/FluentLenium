package org.fluentlenium.adapter.cucumber.custom.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.adapter.cucumber.custom.page.HomePage;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.core.annotation.Page;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicStep extends FluentCucumberTest{

  @Page
  HomePage page;

  @Given(value = "Visit duckduckgo")
  public void step1() {
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
}
