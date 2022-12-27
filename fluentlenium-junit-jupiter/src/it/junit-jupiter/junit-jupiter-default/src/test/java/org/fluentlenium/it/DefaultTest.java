package io.fluentlenium.it;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DefaultTest extends FluentTest {
    @BeforeAll
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void test() {
        assertTrue(true);
    }
}
