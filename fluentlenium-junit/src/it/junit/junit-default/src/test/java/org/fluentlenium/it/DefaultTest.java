package org.fluentlenium.it;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.fluentlenium.adapter.junit.FluentTest;
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
