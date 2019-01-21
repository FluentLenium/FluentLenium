package org.fluentlenium.integration;

import org.fluentlenium.core.hook.wait.WaitHook;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WaitHookWithTest extends IntegrationFluentTest {

    @Test
    void testWaiting() {
        goTo(JAVASCRIPT_URL);
        find("#newField").withHook(WaitHook.class).click();
    }

    @Test
    void testWaitingNotFound() {
        goTo(JAVASCRIPT_URL);
        assertThatThrownBy(() -> find("#anotherField").withHook(WaitHook.class).click())
                .isExactlyInstanceOf(TimeoutException.class)
                .hasMessageStartingWith("Expected condition failed: waiting for By.cssSelector: #anotherField");

    }
}
