package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HiddenTextTest extends IntegrationFluentTest {

    @Test
    public void checkGetTextWorks() {
        goTo(DEFAULT_URL);
        final FluentWebElement line = el("#hidden");
        assertThat(line.text()).isEmpty();
    }

    @Test
    public void checkGetTextContentWorks() {
        goTo(DEFAULT_URL);
        final FluentWebElement line = el("#hidden");
        assertThat(line.textContent()).isNotEmpty();
    }
}
