package io.fluentlenium.adapter.junit.integration.shareddriver;

import io.fluentlenium.configuration.FluentConfiguration;import io.fluentlenium.core.filter.FilterConstructor;import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.configuration.FluentConfiguration.BooleanValue;
import io.fluentlenium.adapter.junit.integration.IntegrationFluentTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.assertj.core.api.Assertions.assertThat;
import static io.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.JVM, deleteCookies = FluentConfiguration.BooleanValue.TRUE)
class SharedDriverSuperClass extends IntegrationFluentTest {
}

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SharedDriverSuperClassTest extends SharedDriverSuperClass {

    @Test
    public void first() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
        el("#name").fill().with("Slawomir");
    }

    @Test
    public void second() {
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
        assertThat(el("#name").value()).isEqualTo("Slawomir");
    }

}
