package org.fluentlenium.test.actions.window;

import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WindowActionsTest extends IntegrationFluentTest {

    @Test
    void openNewAndSwitchIT() {
        goTo(DEFAULT_URL);

        String hWnd = getDriver().getWindowHandle();
        String wndTitle = getDriver().getTitle();

        assertThat(getDriver().getWindowHandles()).hasSize(1);

        window().openNewAndSwitch();

        assertThat(getDriver().getWindowHandles()).hasSize(2);
        assertThat(getDriver().getTitle()).isNotEqualTo(wndTitle);
        assertThat(getDriver().getWindowHandle()).isNotEqualTo(hWnd);
    }

    @Test
    void clickAndOpenNewAndCloseCurrentIT() {
        goTo(DEFAULT_URL);
        String hWnd = getDriver().getWindowHandle();
        assertThat(getDriver().getWindowHandles()).hasSize(1);

        window().clickAndOpenNew(el("#linkToAlertPage"));

        assertThat(getDriver().getWindowHandles()).hasSize(2);
        assertThat(getDriver().getWindowHandle()).isNotEqualTo(hWnd);

        window().clickAndCloseCurrent(el("#closeMe"));

        assertThat(getDriver().getWindowHandles()).hasSize(1);
        assertThat(getDriver().getWindowHandle()).isEqualTo(hWnd);
    }

}
