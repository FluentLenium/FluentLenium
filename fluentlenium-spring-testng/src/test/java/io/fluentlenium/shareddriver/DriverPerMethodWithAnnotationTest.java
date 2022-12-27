package io.fluentlenium.shareddriver;

import io.fluentlenium.IntegrationFluentTestNg;
import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.core.filter.FilterConstructor;
import org.testng.annotations.Test;

import static io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.METHOD;
import static org.assertj.core.api.Assertions.assertThat;

@FluentConfiguration(driverLifecycle = METHOD)
public class DriverPerMethodWithAnnotationTest extends IntegrationFluentTestNg {

    @Test
    public void firstMethod() {
        goTo(IntegrationFluentTestNg.DEFAULT_URL);
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
    }

    @Test
    public void secondMethod() {
        assertThat($(".small", FilterConstructor.withName("name"))).isEmpty();
    }

}
