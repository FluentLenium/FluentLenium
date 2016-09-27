package org.fluentlenium.integration;

import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.hook.wait.Wait;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Wait
public class WaitHookAnnotationTest extends IntegrationFluentTest {
    private FluentWebElement newField;

    private FluentWebElement anotherField;

    @Test
    public void testWaiting() {
        goTo(JAVASCRIPT_URL);
        find("#newField").click();
    }

    @Test
    public void testWaitingNotFound() {
        goTo(JAVASCRIPT_URL);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                find("#anotherField").click();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class).hasCauseExactlyInstanceOf(TimeoutException.class);

    }

    @Test
    public void testWaitingInject() {
        goTo(JAVASCRIPT_URL);
        assertThat(newField.isLoaded()).isFalse();
        newField.click();
        assertThat(newField.isLoaded()).isTrue();
        assertThat(newField.present()).isTrue();
    }

    @Test
    public void testWaitingInjectNotFound() {
        goTo(JAVASCRIPT_URL);
        assertThat(anotherField.isLoaded()).isFalse();
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                anotherField.click();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class).hasCauseExactlyInstanceOf(TimeoutException.class);
    }
}
