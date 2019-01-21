package org.fluentlenium.integration;

import org.junit.jupiter.api.Test;

class ActionOnSelectorAdvancedUserIntegrationTest extends AbstractActionOnSelectorAdvancedUserIntegrationTest {
    @Test
    void checkDoubleClickAction() {
        checkDoubleClick();
    }

    @Test
    void checkMouseOverAction() {
        checkMouseOver();
    }
}
