package io.fluentlenium.test.await.hook;

import io.fluentlenium.core.hook.wait.WaitHook;
import io.fluentlenium.core.hook.wait.WaitHook;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WaitHookWithTest extends IntegrationFluentTest {

    @Test
    void testWaitingEl() {
        goTo(JAVASCRIPT_URL);
        el("#newField").withHook(WaitHook.class).click();
    }

    @Test
    void testWaitingFind() {
        goTo(JAVASCRIPT_URL);
        find("#newField").withHook(WaitHook.class).click();
    }

    @Test
    void testWaitingNotFound() {
        goTo(JAVASCRIPT_URL);
        assertThatThrownBy(() -> el("#anotherField").withHook(WaitHook.class).click())
                .isExactlyInstanceOf(TimeoutException.class)
                .hasMessageStartingWith("Expected condition failed: waiting for By.cssSelector: #anotherField");

    }
}
