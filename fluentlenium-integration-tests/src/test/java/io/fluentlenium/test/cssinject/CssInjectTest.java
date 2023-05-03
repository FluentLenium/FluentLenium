package io.fluentlenium.test.cssinject;

import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
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

    @Test
    void injectDisplayNoneNonEscapedShouldMakeElementNotPresent() {
        goTo(DEFAULT_URL);
        assertThat(location.displayed()).isTrue();
        css().inject("#location {\ndisplay: none\n}");
        assertThat(location.displayed()).isFalse();
    }
}
