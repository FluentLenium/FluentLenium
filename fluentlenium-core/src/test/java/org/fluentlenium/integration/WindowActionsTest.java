package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class WindowActionsTest extends IntegrationFluentTest {
    @Test
    public void openNewAndSwitchIT() {
        goTo(DEFAULT_URL);

        String hWnd = getDriver().getWindowHandle();
        String wndTitle = getDriver().getTitle();

        assertThat(getDriver().getWindowHandles().size()).isEqualTo(1);

        window().openNewAndSwitch();

        assertThat(getDriver().getWindowHandles().size()).isEqualTo(2);
        assertThat(getDriver().getTitle()).isNotEqualTo(wndTitle);
        assertThat(getDriver().getWindowHandle()).isNotEqualTo(hWnd);
    }

    @Test
    public void clickAndOpenNewAndCloseCurrentIT() {
        goTo(DEFAULT_URL);
        String hWnd = getDriver().getWindowHandle();
        assertThat(getDriver().getWindowHandles().size()).isEqualTo(1);

        window().clickAndOpenNew(el("#linkToAlertPage"));

        assertThat(getDriver().getWindowHandles().size()).isEqualTo(2);
        assertThat(getDriver().getWindowHandle()).isNotEqualTo(hWnd);

        window().clickAndCloseCurrent(el("#closeMe"));

        assertThat(getDriver().getWindowHandles().size()).isEqualTo(1);
        assertThat(getDriver().getWindowHandle()).isEqualTo(hWnd);
    }

    @Override
    public WebDriver newWebDriver() {
        return new HtmlUnitDriver(true);
    }
}
