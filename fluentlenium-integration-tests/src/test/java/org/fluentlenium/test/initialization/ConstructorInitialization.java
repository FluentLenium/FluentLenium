package org.fluentlenium.test.initialization;

import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstructorInitialization extends FluentTest {
    private final WebDriver webDriver = new HtmlUnitDriver();

    @Test
    void doNotUseOverridableMethodsInAConstructor() {
        assertThat(webDriver).isEqualTo(getDriver());
    }

    @Override
    public WebDriver newWebDriver() {
        return webDriver;
    }
}
