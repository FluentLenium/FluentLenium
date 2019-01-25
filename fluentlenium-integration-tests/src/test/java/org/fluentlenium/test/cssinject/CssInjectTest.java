package org.fluentlenium.test.cssinject;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CssInjectTest extends IntegrationFluentTest {

    private FluentWebElement location;

    @Test
    void injectDisplayNoneShouldMakeElementNotPresent() {
        goTo(DEFAULT_URL);
        assertThat(location.displayed()).isTrue();
        css().inject("#location {display: none}");
        assertThat(location.displayed()).isFalse();
    }
}
