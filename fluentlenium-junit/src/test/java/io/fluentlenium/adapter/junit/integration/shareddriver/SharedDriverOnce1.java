package io.fluentlenium.adapter.junit.integration.shareddriver;

import io.fluentlenium.adapter.junit.integration.IntegrationFluentTest;
import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.core.filter.FilterConstructor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.assertj.core.api.Assertions.assertThat;

@FluentConfiguration(driverLifecycle = DriverLifecycle.JVM)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SharedDriverOnce1 extends IntegrationFluentTest {

    @Test
    public void firstMethod() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
    }

    @Test
    public void secondMethod() {
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
    }

}
