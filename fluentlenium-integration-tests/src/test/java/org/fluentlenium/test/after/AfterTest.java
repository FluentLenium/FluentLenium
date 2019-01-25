package org.fluentlenium.test.after;

import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class AfterTest extends FluentTest {

    @Override
    public WebDriver newWebDriver() {
        return null;
    }

    @Test
    void whenDriverIsNullThenItIsHandle() {
        // after();
    }
}
