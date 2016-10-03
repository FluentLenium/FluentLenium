package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CssInjectTest extends IntegrationFluentTest {
    private FluentWebElement location;

    @Test
    public void injectDisplayNoneShouldMakeElementNotPresent() {
        goTo(DEFAULT_URL);
        assertThat(location.displayed()).isTrue();
        css().inject("#location {display: none}");
        assertThat(location.displayed()).isFalse();
    }
}
