package org.fluentlenium.shareddriver;

import org.fluentlenium.IntegrationFluentTestNg;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.JVM)
@Test(dependsOnGroups = "DriverOnce1", suiteName = "Once")
public class DriverOnce2Test extends IntegrationFluentTestNg {

    @Test
    public void firstMethod() {
        assertThat($(".small", withName("name"))).hasSize(1);
    }

}
