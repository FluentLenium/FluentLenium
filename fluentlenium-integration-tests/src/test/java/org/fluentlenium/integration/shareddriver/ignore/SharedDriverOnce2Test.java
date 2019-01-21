package org.fluentlenium.integration.shareddriver.ignore;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.JVM)
class SharedDriverOnce2Test extends IntegrationFluentTest {

    @Test
    void secondMethod() {
        assertThat($(".small", withName("name"))).hasSize(1);
    }

}
