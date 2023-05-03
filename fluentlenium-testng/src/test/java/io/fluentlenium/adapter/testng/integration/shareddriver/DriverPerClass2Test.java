package io.fluentlenium.adapter.testng.integration.shareddriver;

import io.fluentlenium.adapter.testng.integration.localtest.IntegrationFluentTestNg;
import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.core.filter.FilterConstructor;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@FluentConfiguration(driverLifecycle = DriverLifecycle.CLASS)
@Test(dependsOnGroups = "DriverPerClass1", suiteName = "PerClass")
public class DriverPerClass2Test extends IntegrationFluentTestNg {

    @Test
    public void firstMethod() {
        assertThat($(".small", FilterConstructor.withName("name"))).isEmpty();
    }

}
