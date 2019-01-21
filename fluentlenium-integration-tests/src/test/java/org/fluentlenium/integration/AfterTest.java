package org.fluentlenium.integration;

import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class AfterTest extends FluentTest {

    @Override
    public WebDriver newWebDriver() {
        return null;    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Test
    void whenDriverIsNullThenItIsHandle() {
        // after();
    }
}
