package org.fluentlenium.integration.initialization;

import org.fluentlenium.integration.util.adapter.FluentTest;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstructorInitialization extends FluentTest {
    private WebDriver webDriver = new HtmlUnitDriver();

    @Test
    public void doNotUseOverridableMethodsInAConstructor() {
        assertThat(webDriver).isEqualTo(this.getDriver());
    }

    @Override
    public WebDriver newWebDriver() {
        return webDriver;
    }
}
