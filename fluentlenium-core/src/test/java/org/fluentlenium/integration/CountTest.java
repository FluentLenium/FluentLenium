package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CountTest extends IntegrationFluentTest {
    @Test
    public void count() {
        goTo(COUNT_URL);
        long l = System.currentTimeMillis();
        FluentList<FluentWebElement> count = find(".count");
        assertThat(count.count()).isEqualTo(5000);
        assertThat(count.loaded()).isFalse();
    }
}
