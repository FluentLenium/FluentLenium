package org.fluentlenium.integration;

import org.fluentlenium.core.hook.wait.WaitHook;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WaitHookWithTest extends IntegrationFluentTest {
    @Test
    public void testWaiting() {
        goTo(JAVASCRIPT_URL);
        find("#newField").withHook(WaitHook.class).click();
    }

    @Test
    public void testWaitingNotFound() {
        goTo(JAVASCRIPT_URL);
        assertThatThrownBy(() -> find("#anotherField").withHook(WaitHook.class).click())
                .isExactlyInstanceOf(TimeoutException.class)
                .hasMessageStartingWith("Expected condition failed: waiting for By.cssSelector: #anotherField");

    }
}
