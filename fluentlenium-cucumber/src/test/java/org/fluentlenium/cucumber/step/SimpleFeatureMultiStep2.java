package org.fluentlenium.cucumber.step;

import cucumber.api.java.After;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.cucumber.adapter.FluentCucumberTest;
import org.fluentlenium.cucumber.adapter.util.SharedDriver;
import org.fluentlenium.cucumber.page.LocalPage2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@SharedDriver(type = SharedDriver.SharedType.PER_FEATURE)
public class SimpleFeatureMultiStep2 extends FluentCucumberTest {

    @Page
    LocalPage2 page2;

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver();
    }

    @When(value = "feature multi2 I click on next page")
    public void step2() {
        this.initFluent();
        this.init();

        click("a#linkToPage2");
    }

    @Then(value = "feature multi2 I am on the second page")
    public void step3() {
        this.initFluent();
        this.init();

        page2.isAt();
    }

    @After
    public void after() {
        this.quit();
    }

}
