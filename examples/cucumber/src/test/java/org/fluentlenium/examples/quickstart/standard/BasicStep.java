package org.fluentlenium.examples.quickstart;

import static org.assertj.core.api.Assertions.assertThat;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.fluentlenium.examples.quickstart.page.HomePage;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;

public class BasicStep extends FluentCucumberTest {

  @Page
  HomePage page;

  @Override
  public WebDriver newWebDriver() {
    return new ChromeDriver();
  }

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
    assertThat(window().title()).contains("FluentLenium");
  }

  @Before
  public void before(Scenario scenario) {
    super.before(scenario);
  }

  @After
  public void after(Scenario scenario) {
    super.after(scenario);
  }
}
