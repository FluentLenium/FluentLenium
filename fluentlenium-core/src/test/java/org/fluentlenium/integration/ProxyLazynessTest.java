package org.fluentlenium.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

public class ProxyLazynessTest extends IntegrationFluentTest {

    @Test
    public void testMissingElementList() {
        FluentList<FluentWebElement> fluentWebElements = find("#missing");

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                fluentWebElements.now();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testMissingElement() {
        FluentWebElement fluentWebElement = el("#missing");

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                fluentWebElement.now();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testChainElements() {
        goTo(DEFAULT_URL);

        FluentWebElement fluentWebElement = el("#oneline").el("#missing2").el("missing3");

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                fluentWebElement.now();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testChainList() {
        goTo(DEFAULT_URL);

        FluentList<FluentWebElement> fluentWebElements = $("#oneline").$("#missing2").$("missing3");

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                fluentWebElements.now();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);
    }
}
