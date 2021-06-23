package org.fluentlenium.adapter.junit.integration.shareddriver;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.configuration.FluentConfiguration.BooleanValue;
import org.fluentlenium.adapter.junit.integration.IntegrationFluentTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.JVM, deleteCookies = BooleanValue.TRUE)
class SharedDriverSuperClass extends IntegrationFluentTest {
}

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SharedDriverSuperClassTest extends SharedDriverSuperClass {

    @Test
    public void first() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
        el("#name").fill().with("Slawomir");
    }

    @Test
    public void second() {
        assertThat($(".small", withName("name"))).hasSize(1);
        assertThat(el("#name").value()).isEqualTo("Slawomir");
    }

}
