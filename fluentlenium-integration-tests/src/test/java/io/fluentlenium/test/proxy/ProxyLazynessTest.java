package io.fluentlenium.test.proxy;

import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;

class ProxyLazynessTest extends IntegrationFluentTest {

    @Test
    void testMissingElementList() {
        FluentList<FluentWebElement> fluentWebElements = find("#missing");

        Assertions.assertThatThrownBy(fluentWebElements::now).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testMissingElement() {
        FluentWebElement fluentWebElement = el("#missing");

        Assertions.assertThatThrownBy(fluentWebElement::now).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testChainElements() {
        goTo(DEFAULT_URL);

        FluentWebElement fluentWebElement = el("#oneline").el("#missing2").el("missing3");

        Assertions.assertThatThrownBy(fluentWebElement::now).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testChainList() {
        goTo(DEFAULT_URL);

        FluentList<FluentWebElement> fluentWebElements = $("#oneline").$("#missing2").$("missing3");

        Assertions.assertThatThrownBy(fluentWebElements::now).isExactlyInstanceOf(NoSuchElementException.class);
    }
}
