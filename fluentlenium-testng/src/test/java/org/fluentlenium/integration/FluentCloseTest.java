package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class FluentCloseTest extends LocalFluentCase {

    @Test
    public void when_default_shared_driver_then_driver_is_closed_after_method_call() {
        goTo("http://www.google.com");
    }

    @AfterClass
    public void afterClass() {
        try {
            getDriver().get("http://www.yahoo.fr");
            fail("should have a SessionNotFoundException because driver is closed");
        } catch (WebDriverException e) {

        }
    }

}
