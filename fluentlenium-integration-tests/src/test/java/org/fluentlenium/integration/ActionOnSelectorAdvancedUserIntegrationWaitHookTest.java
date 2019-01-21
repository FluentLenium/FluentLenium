package org.fluentlenium.integration;

import org.fluentlenium.core.hook.wait.Wait;
import org.junit.jupiter.api.Test;

@Wait
class ActionOnSelectorAdvancedUserIntegrationWaitHookTest extends AbstractActionOnSelectorAdvancedUserIntegrationTest {

    @Test
    void checkDoubleClickAction() {
        checkDoubleClick();
    }

    @Test
    void checkMouseOverAction() {
        checkMouseOver();
    }
}
