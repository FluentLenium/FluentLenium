package io.fluentlenium.adapter.junit.integration.shareddriver;

import io.fluentlenium.adapter.junit.integration.IntegrationFluentTest;
import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.core.filter.FilterConstructor;
import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.adapter.junit.integration.IntegrationFluentTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static io.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.CLASS)
public class SharedDriverPerClass2 extends IntegrationFluentTest {

    @Test
    public void secondMethod() {
        assertThat($(".small", FilterConstructor.withName("name"))).isEmpty();
    }

}
