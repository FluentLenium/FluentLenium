package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FluentWebElementInTest extends IntegrationFluentTest {
    private FluentWebElement linkToPage2;

    @Test
    void whenWebElementInTestThenTheyAreInstantiated() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        linkToPage2.click();
        assertThat(url()).isEqualTo(IntegrationFluentTest.PAGE_2_URL);
    }

}
