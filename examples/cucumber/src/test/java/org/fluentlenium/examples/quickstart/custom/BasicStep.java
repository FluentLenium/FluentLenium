package org.fluentlenium.adapter.cucumber.custom;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.adapter.cucumber.custom.page.HomePage;
import org.fluentlenium.core.annotation.Page;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicStep {

  @Page
  HomePage page;

  @Given(value = "Visit duckduckgo")
  public void step1() {
    page.go();
  }

  @When(value = "I search FluentLenium")
  public void step2() {
    page.el("#search_form_input_homepage").fill().with("FluentLenium");
    page.el("#search_button_homepage").submit();
  }

  @Then(value = "Title contains FluentLenium")
  public void step3() {
    assertThat(page.window().title()).contains("FluentLenium");
  }
}
