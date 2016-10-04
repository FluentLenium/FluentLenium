package org.fluentlenium.integration.ignore;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.JVM)
public class SharedDriverOnce2 extends IntegrationFluentTest {

    @Test
    public void secondMethod() {
        assertThat($(".small", withName("name"))).hasSize(1);
    }

}
