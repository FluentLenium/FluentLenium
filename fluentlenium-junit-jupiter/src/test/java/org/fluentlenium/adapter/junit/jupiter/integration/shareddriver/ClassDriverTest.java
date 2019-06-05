package org.fluentlenium.adapter.junit.jupiter.integration.shareddriver;

import org.fluentlenium.adapter.junit.jupiter.integration.IntegrationFluentTest;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.CLASS)
class ClassDriverTest extends IntegrationFluentTest {

    @Test
    @Order(1)
    void firstMethod() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
    }

    @Test
    @Order(2)
    void secondMethod() {
        assertThat($(".small", withName("name"))).hasSize(1);
    }

}
