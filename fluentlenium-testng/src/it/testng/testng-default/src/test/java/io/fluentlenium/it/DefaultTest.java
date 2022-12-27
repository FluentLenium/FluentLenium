package io.fluentlenium.it;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.fluentlenium.adapter.testng.FluentTestNg;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DefaultTest extends FluentTestNg {

    @BeforeClass
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void test() {
        Assert.assertTrue(true);
    }
}
