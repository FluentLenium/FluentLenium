package io.fluentlenium.it;

import io.fluentlenium.adapter.junit.jupiter.FluentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
