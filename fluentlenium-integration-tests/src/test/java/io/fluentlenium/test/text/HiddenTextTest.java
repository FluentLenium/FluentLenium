package io.fluentlenium.test.text;

import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
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
