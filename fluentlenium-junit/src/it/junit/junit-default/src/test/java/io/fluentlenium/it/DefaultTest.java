package io.fluentlenium.it;

import io.fluentlenium.adapter.junit.FluentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultTest extends FluentTest {

    @BeforeClass
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void test() {
        Assert.assertTrue(true);
    }
}
