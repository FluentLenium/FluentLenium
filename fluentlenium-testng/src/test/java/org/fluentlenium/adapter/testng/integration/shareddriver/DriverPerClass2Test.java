package org.fluentlenium.adapter.testng.integration.shareddriver;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.adapter.testng.integration.localtest.LocalFluentCase;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.CLASS)
@Test(dependsOnGroups = "DriverPerClass1", suiteName = "PerClass")
public class DriverPerClass2Test extends LocalFluentCase {

    @Test
    public void firstMethod() {
        assertThat($(".small", withName("name"))).hasSize(0);
    }


}
