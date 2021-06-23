package org.fluentlenium.test.param;

import org.fluentlenium.test.IntegrationFluentTest;
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
        assertThat(url()).contains("index.html");
    }

    @Test
    void checkPageSource() {
        goTo(DEFAULT_URL);
        assertThat(pageSource()).contains("body");
    }
}
