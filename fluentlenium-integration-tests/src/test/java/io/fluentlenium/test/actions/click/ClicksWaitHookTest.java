package io.fluentlenium.test.actions.click;

import io.fluentlenium.core.hook.wait.Wait;
import io.fluentlenium.core.hook.wait.Wait;
import org.junit.jupiter.api.Test;

@Wait
class ClicksWaitHookTest extends AbstractClicksTest {

    @Test
    void checkDoubleClickAction() {
        checkDoubleClick();
    }

    @Test
    void checkMouseOverAction() {
        checkMouseOver();
    }
}
