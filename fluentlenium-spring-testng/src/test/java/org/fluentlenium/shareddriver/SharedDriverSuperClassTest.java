package org.fluentlenium.shareddriver;

import org.fluentlenium.IntegrationFluentTestNg;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.CLASS)
class SharedDriverSuperClass extends IntegrationFluentTestNg {
}

public class SharedDriverSuperClassTest extends SharedDriverSuperClass {
    @Test
    public void firstMethod() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
        el("#name").fill().with("Slawomir");
    }

    @Test
    public void secondMethod() {
        assertThat($(".small", withName("name"))).hasSize(1);
        assertThat(el("#name").value()).isEqualTo("Slawomir");
    }

}
