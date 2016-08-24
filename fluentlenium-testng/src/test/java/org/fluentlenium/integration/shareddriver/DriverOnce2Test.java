package org.fluentlenium.integration.shareddriver;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.JVM)
@Test(dependsOnGroups = "DriverOnce1", suiteName = "Once")
public class DriverOnce2Test extends LocalFluentCase {

    @Test
    public void firstMethod() {
        assertThat($(".small", withName("name"))).hasSize(1);
    }


}
