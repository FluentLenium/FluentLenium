package org.fluentlenium.it;

import org.fluentlenium.adapter.junit5.FluentTest;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class DefaultTest extends FluentTest {
    @Override
    public WebDriver newWebDriver() {
        return new HtmlUnitDriver(true);
    }

    @Test
    public void test() {
        Assert.assertTrue(true);
    }
}
