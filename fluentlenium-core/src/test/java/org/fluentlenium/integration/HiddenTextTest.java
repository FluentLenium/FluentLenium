package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HiddenTextTest extends LocalFluentCase {

    @Test
    public void checkGetTextWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement line = findFirst("#hidden");
        assertThat(line.getText()).isEmpty();
    }

    @Test
    public void checkGetTextContentWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement line = findFirst("#hidden");
        assertThat(line.getTextContent()).isNotEmpty();
    }
}
