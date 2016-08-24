package org.fluentlenium.adapter.testng.integration;

import org.fluentlenium.adapter.testng.integration.localtest.LocalFluentCase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class FluentCloseTest extends LocalFluentCase {

    @Test
    public void when_default_shared_driver_then_driver_is_closed_after_method_call() {
        goTo("about:blank");
    }

    @AfterClass
    public void afterClass() {
        try {
            getDriver().get("about:blank");
            fail("should have a NullPointerException because driver is closed");
        } catch (NullPointerException e) {

        }
    }

}
