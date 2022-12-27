package io.fluentlenium.adapter.testng.integration.shareddriver;

import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.core.filter.FilterConstructor;
import io.fluentlenium.adapter.testng.integration.localtest.IntegrationFluentTestNg;
import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import io.fluentlenium.configuration.FluentConfiguration;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static io.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.JVM)
@Test(dependsOnGroups = "DriverOnce1", suiteName = "Once")
public class DriverOnce2Test extends IntegrationFluentTestNg {

    @Test
    public void firstMethod() {
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
    }

}
