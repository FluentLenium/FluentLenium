package org.fluentlenium.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

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
        assertThat(line.getTextContent()).isNullOrEmpty();
    }
}
