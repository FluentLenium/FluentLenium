package io.fluentlenium.test.fluentwebelement;

import io.fluentlenium.core.domain.FluentWebElement;import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FluentWebElementInTest extends IntegrationFluentTest {
    private FluentWebElement linkToPage2;

    @Test
    void whenWebElementInTestThenTheyAreInstantiated() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        linkToPage2.click();
        assertThat(url()).contains("page2.html");
    }

}
