package org.fluentlenium.test.actions.click;

import org.fluentlenium.core.hook.wait.Wait;
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
