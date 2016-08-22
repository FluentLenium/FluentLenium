package org.fluentlenium.integration.initialization;

import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstructorInitialization extends FluentTest {
    public WebDriver webDriver = new HtmlUnitDriver();


    @Test
    public void do_not_use_overridable_methods_in_a_constructor() {
        assertThat(webDriver).isEqualTo(this.getDriver());
    }


    @Override
    public WebDriver newWebDriver() {
        return webDriver;
    }
}
