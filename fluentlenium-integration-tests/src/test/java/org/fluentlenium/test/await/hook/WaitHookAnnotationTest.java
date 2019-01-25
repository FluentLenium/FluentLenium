package org.fluentlenium.test.await.hook;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.hook.wait.Wait;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Wait
class WaitHookAnnotationTest extends IntegrationFluentTest {
    private FluentWebElement newField;

    private FluentWebElement anotherField;

    @Test
    void testWaiting() {
        goTo(JAVASCRIPT_URL);
        find("#newField").click();
    }

    @Test
    void testWaitingNotFound() {
        goTo(JAVASCRIPT_URL);
        assertThatThrownBy(() -> find("#anotherField").click()).isExactlyInstanceOf(TimeoutException.class)
                .hasMessageStartingWith("Expected condition failed: waiting for By.cssSelector: #anotherField");

    }

    @Test
    void testWaitingInject() {
        goTo(JAVASCRIPT_URL);
        assertThat(newField.loaded()).isFalse();
        newField.click();
        assertThat(newField.loaded()).isTrue();
        assertThat(newField.present()).isTrue();
    }

    @Test
    void testWaitingInjectNotFound() {
        goTo(JAVASCRIPT_URL);
        assertThat(anotherField.loaded()).isFalse();
        assertThatThrownBy(() -> anotherField.click()).isExactlyInstanceOf(TimeoutException.class);
    }
}
