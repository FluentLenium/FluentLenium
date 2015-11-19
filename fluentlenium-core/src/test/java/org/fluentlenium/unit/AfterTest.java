package org.fluentlenium.unit;

import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;
import org.openqa.selenium.WebDriver;


public class AfterTest extends FluentTest {

    @Override
    public WebDriver getDefaultDriver() {
        return null;    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Test
    public void when_driver_is_null_then_it_is_handle() {
        // after();
    }
}
