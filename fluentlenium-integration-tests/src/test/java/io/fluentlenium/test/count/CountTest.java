package io.fluentlenium.test.count;

import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountTest extends IntegrationFluentTest {

    @Test
    void count() {
        goTo(COUNT_URL);
        FluentList<FluentWebElement> count = find(".count");
        assertThat(count.count()).isEqualTo(5000);
        Assertions.assertThat(count.loaded()).isFalse();
    }
}
