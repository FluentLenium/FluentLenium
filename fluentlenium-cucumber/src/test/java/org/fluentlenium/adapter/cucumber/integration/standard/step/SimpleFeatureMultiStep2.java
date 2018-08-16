package org.fluentlenium.adapter.cucumber.integration.standard.step;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage2;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SimpleFeatureMultiStep2 extends FluentCucumberTest {

    @Page
    private LocalPage2 page2;

    @Override
    public WebDriver newWebDriver() {
        return new HtmlUnitDriver();
    }

    @When(value = "feature multi2 I click on next page")
    public void step2() {
        $("a#linkToPage2").click();
    }

    @Then(value = "feature multi2 I am on the second page")
    public void step3() {
        page2.isAt();
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
