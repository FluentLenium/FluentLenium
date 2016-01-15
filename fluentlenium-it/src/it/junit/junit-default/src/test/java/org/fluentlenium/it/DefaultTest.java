package org.fluentlenium.it;

import org.fluentlenium.adapter.FluentTest;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class DefaultTest extends FluentTest {
    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver(true);
    }

    @Test
    public void test() {
        Assert.assertTrue(true);
    }
}
