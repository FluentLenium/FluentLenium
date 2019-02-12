package org.fluentlenium.test.text;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HiddenTextTest extends IntegrationFluentTest {

    @Test
    void checkGetTextWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement line = el("#hidden");
        assertThat(line.text()).isEmpty();
    }

    @Test
    void checkGetTextContentWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement line = el("#hidden");
        assertThat(line.textContent()).isNotEmpty();
    }
}
