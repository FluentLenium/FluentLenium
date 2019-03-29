package org.fluentlenium.test.actions.waitandclick;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import java.time.Duration;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.FindBy;

class WaitAndClickTest extends IntegrationFluentTest {

    @FindBy(css = "button#first")
    private FluentWebElement firstButton;

    @FindBy(css = "button#second")
    private FluentWebElement secondButton;

    @FindBy(css = "button")
    private FluentList<FluentWebElement> buttons;

    @Test
    void waitAndClickElementPositiveTest() {
        goTo(CLICK_URL);
        assertThat(firstButton).isNotClickable();
        firstButton.waitAndClick();
        await().until(firstButton).not().clickable();
    }

    @Test
    void waitAndClickElementNegativeTest() {
        goTo(CLICK_URL);
        assertThat(secondButton).isNotClickable();
        assertThatThrownBy(() -> secondButton.waitAndClick(Duration.ofMillis(1)))
                .isInstanceOf(TimeoutException.class)
                .hasMessageContaining("By.cssSelector: button#second");
    }

    @Test
    void waitAndClickListPositiveTest() {
        goTo(CLICK_URL);
        assertThat(buttons.get(0)).isNotClickable();
        assertThat(buttons.get(1)).isNotClickable();
        buttons.waitAndClick();
        await().until(buttons.get(0)).not().clickable();
    }

    @Test
    void waitAndClickListWithDurationPositiveTest() {
        goTo(CLICK_URL);
        assertThat(buttons.get(0)).isNotClickable();
        assertThat(buttons.get(1)).isNotClickable();
        buttons.waitAndClick(Duration.ofSeconds(2));
        await().until(buttons.get(0)).not().clickable();
    }

    @Test
    void waitAndClickListNegativeTest() {
        goTo(CLICK_URL);
        assertThat(buttons.get(0)).isNotClickable();
        assertThat(buttons.get(1)).isNotClickable();
        assertThatThrownBy(() -> buttons.waitAndClick(Duration.ofMillis(1)))
                .isInstanceOf(TimeoutException.class)
                .hasMessageContaining("By.cssSelector: button");
    }

}
