package org.fluentlenium.integration;

import org.fluentlenium.core.hook.wait.Wait;
import org.junit.Test;

@Wait
public class ActionOnSelectorAdvancedUserIntegrationWaitHookTest extends AbstractActionOnSelectorAdvancedUserIntegrationTest {
    @Test
    public void checkDoubleClickAction() {
        checkDoubleClick();
    }

    @Test
    public void checkMouseOverAction() {
        checkMouseOver();
    }
}
