package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParamTest extends IntegrationFluentTest {

    @Test
    void checkTitleParam() {
        goTo(DEFAULT_URL);
        assertThat(window().title()).contains("Selenium");
    }

    @Test
    void checkUrlParam() {
        goTo(DEFAULT_URL);
        assertThat(url()).isEqualTo(DEFAULT_URL);
    }

    @Test
    void checkPageSource() {
        goTo(DEFAULT_URL);
        assertThat(pageSource()).contains("body");
    }
}
