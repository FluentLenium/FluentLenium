package org.fluentlenium.adapter.junit.integration.shareddriver;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.adapter.junit.integration.IntegrationFluentTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.METHOD)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SharedDriverPerMethodByAnnotation extends IntegrationFluentTest {

    @Test
    public void firstMethod() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
    }

    @Test
    public void secondMethod() {
        assertThat($(".small", withName("name"))).hasSize(0);
    }

}
