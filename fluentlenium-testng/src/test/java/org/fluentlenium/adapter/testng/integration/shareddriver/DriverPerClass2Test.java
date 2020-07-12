package org.fluentlenium.adapter.testng.integration.shareddriver;

import org.fluentlenium.adapter.testng.integration.localtest.IntegrationFluentTestNg;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.CLASS)
@Test(dependsOnGroups = "DriverPerClass1", suiteName = "PerClass")
public class DriverPerClass2Test extends IntegrationFluentTestNg {

    @Test
    public void firstMethod() {
        assertThat($(".small", withName("name"))).isEmpty();
    }

}
