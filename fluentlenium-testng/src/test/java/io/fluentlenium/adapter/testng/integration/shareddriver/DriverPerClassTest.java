package io.fluentlenium.adapter.testng.integration.shareddriver;

import io.fluentlenium.adapter.testng.integration.localtest.IntegrationFluentTestNg;
import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.core.filter.FilterConstructor;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@FluentConfiguration(driverLifecycle = DriverLifecycle.CLASS)
@Test(groups = "DriverPerClass1", suiteName = "PerClass")
public class DriverPerClassTest extends IntegrationFluentTestNg {

    @Test
    public void firstMethod() {
        goTo(IntegrationFluentTestNg.DEFAULT_URL);
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
    }

    @Test
    public void secondMethod() {
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
    }

}
