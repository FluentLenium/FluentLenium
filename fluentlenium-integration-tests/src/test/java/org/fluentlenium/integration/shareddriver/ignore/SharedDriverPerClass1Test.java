package org.fluentlenium.integration.shareddriver.ignore;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SharedDriverPerClass1Test extends IntegrationFluentTest {

    @Test
    void firstMethod() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
    }

    @Test
    void secondMethod() {
        assertThat($(".small", withName("name"))).hasSize(1);
    }

}
