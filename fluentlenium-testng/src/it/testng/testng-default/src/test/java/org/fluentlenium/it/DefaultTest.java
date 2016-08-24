package org.fluentlenium.it;

import org.fluentlenium.adapter.testng.FluentTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DefaultTest extends FluentTestNg {
    @Override
    public WebDriver newWebDriver() {
        return new HtmlUnitDriver(true);
    }

    @Test
    public void test() {
        Assert.assertTrue(true);
    }
}
