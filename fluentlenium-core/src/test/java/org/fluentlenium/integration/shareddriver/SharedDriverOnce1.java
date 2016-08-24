package org.fluentlenium.integration.shareddriver;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.JVM)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SharedDriverOnce1 extends LocalFluentCase {

    @Test
    public void firstMethod() {
        goTo(LocalFluentCase.DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
    }


    @Test
    public void secondMethod() {
        assertThat($(".small", withName("name"))).hasSize(1);
    }


}
