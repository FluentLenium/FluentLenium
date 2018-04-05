package org.fluentlenium.it;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.fluentlenium.adapter.junit5.FluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class DefaultTest extends FluentTest {
    @Override
    public WebDriver newWebDriver() {
        return new HtmlUnitDriver(true);
    }

    @Test
    public void test() {
        assertTrue(true);
    }
}
