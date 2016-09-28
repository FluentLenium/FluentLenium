package org.fluentlenium.integration;

import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.wait.FluentWait;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FluentWaitMessageTest extends IntegrationFluentTest {
    @Override
    public FluentWait await() {
        return super.await().atMost(100).pollingEvery(10);
    }

    @Test
    public void testDisabled() {
        goTo(DEFAULT_URL);
        final FluentWebElement first = $("#disabled").first();
        assertThat(first.toString()).isEqualTo("By.cssSelector: #disabled (first) (Lazy)");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                first.await().until().enabled();
            }
        }).hasMessageStartingWith("Timed out after 0 seconds: Element By.cssSelector: #disabled (first) (Lazy) is not enabled")
                .isExactlyInstanceOf(TimeoutException.class);

        first.now();
        assertThat(first.toString()).isEqualTo("<input id=\"disabled\" type=\"checkbox\" value=\"John\" disabled=\"disabled\" />");

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                first.await().until().enabled();
            }
        }).hasMessageStartingWith("Timed out after 0 seconds: Element <input id=\"disabled\" type=\"checkbox\" value=\"John\" disabled=\"disabled\" /> is not enabled")
                .isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void testDisabledList() {
        goTo(DEFAULT_URL);
        final FluentList<FluentWebElement> list = $("#disabled");
        assertThat(list.toString()).isEqualTo("[By.cssSelector: #disabled (Lazy)]");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                list.await().until().enabled();
            }
        }).hasMessageStartingWith("Timed out after 0 seconds: Elements [By.cssSelector: #disabled (Lazy)] is not enabled")
                .isExactlyInstanceOf(TimeoutException.class);

        list.now();
        assertThat(list.toString()).isEqualTo("[<input id=\"disabled\" type=\"checkbox\" value=\"John\" disabled=\"disabled\" />]");

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                list.await().until().enabled();
            }
        }).hasMessageStartingWith("Timed out after 0 seconds: Elements [<input id=\"disabled\" type=\"checkbox\" value=\"John\" disabled=\"disabled\" />] is not enabled")
                .isExactlyInstanceOf(TimeoutException.class);

    }

    @Test
    public void testMessageContext() {
        goTo(DEFAULT_URL);
        final FluentWebElement select = $("#select").first();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                select.await().until().rectangle().withWidth().lessThan(0);
            }
        }).hasMessageStartingWith("Timed out after 0 seconds: Element By.cssSelector: #select (first) (Lazy) rectangle width is not less than 0")
                .isExactlyInstanceOf(TimeoutException.class);
    }
}
