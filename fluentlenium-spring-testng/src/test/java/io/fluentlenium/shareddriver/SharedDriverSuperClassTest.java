package io.fluentlenium.shareddriver;

import io.fluentlenium.IntegrationFluentTestNg;
import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.core.filter.FilterConstructor;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@FluentConfiguration(driverLifecycle = DriverLifecycle.CLASS)
class SharedDriverSuperClass extends IntegrationFluentTestNg {
}

public class SharedDriverSuperClassTest extends SharedDriverSuperClass {
    @Test
    public void firstMethod() {
        goTo(IntegrationFluentTestNg.DEFAULT_URL);
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
        el("#name").fill().with("Slawomir");
    }

    @Test
    public void secondMethod() {
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
        assertThat(el("#name").value()).isEqualTo("Slawomir");
    }

}
