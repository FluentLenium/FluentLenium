package io.fluentlenium.adapter.junit.jupiter.integration.shareddriver;

import io.fluentlenium.adapter.junit.jupiter.integration.IntegrationFluentTest;import io.fluentlenium.configuration.FluentConfiguration;import io.fluentlenium.core.filter.FilterConstructor;import io.fluentlenium.adapter.junit.jupiter.integration.IntegrationFluentTest;
import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import io.fluentlenium.configuration.FluentConfiguration;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static io.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.CLASS)
class SuperClass extends IntegrationFluentTest {
}

class SuperClassTest extends SuperClass {

    @Test
    @Order(1)
    void firstMethod() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
    }

    @Test
    @Order(2)
    void secondMethod() {
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
    }

}
