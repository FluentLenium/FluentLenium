package io.fluentlenium.shareddriver;

import io.fluentlenium.IntegrationFluentTestNg;
import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.core.filter.FilterConstructor;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@FluentConfiguration(driverLifecycle = DriverLifecycle.JVM)
@Test(dependsOnGroups = "DriverOnce1", suiteName = "Once")
public class DriverOnce2Test extends IntegrationFluentTestNg {

    @Test
    public void firstMethod() {
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
    }

}
