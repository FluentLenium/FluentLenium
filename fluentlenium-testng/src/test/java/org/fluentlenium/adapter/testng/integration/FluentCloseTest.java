package org.fluentlenium.adapter.testng.integration;

import org.fluentlenium.adapter.testng.integration.localtest.IntegrationFluentTestNg;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class FluentCloseTest extends IntegrationFluentTestNg {

    @Test
    public void whenDefaultSharedDriverThenDriverIsClosedAfterMethodCall() {
        goTo("about:blank");
    }

    @AfterClass
    public void afterClass() {
        try {
            getDriver().get("about:blank");
            fail("should have a NullPointerException because driver is closed");
        } catch (final NullPointerException e) { // NOPMD EmptyCatchBlock

        }
    }

}
