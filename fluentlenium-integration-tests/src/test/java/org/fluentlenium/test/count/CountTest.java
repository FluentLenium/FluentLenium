package org.fluentlenium.test.count;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountTest extends IntegrationFluentTest {

    @Test
    void count() {
        goTo(COUNT_URL);
        long l = System.currentTimeMillis();
        FluentList<FluentWebElement> count = find(".count");
        assertThat(count.count()).isEqualTo(5000);
        assertThat(count.loaded()).isFalse();
    }
}
