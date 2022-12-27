package io.fluentlenium.adapter.testng.integration.shareddriver;

import io.fluentlenium.adapter.testng.integration.localtest.IntegrationFluentTestNg;
import io.fluentlenium.configuration.ConfigurationProperties;
import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.core.filter.FilterConstructor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@FluentConfiguration(driverLifecycle = ConfigurationProperties.DriverLifecycle.THREAD)
public class DriverPerThreadTest extends IntegrationFluentTestNg {
    private List<String> hwnds = new ArrayList<>();

    @Test(invocationCount = 4, threadPoolSize = 3)
    public void firstMethod() {
        goTo(IntegrationFluentTestNg.DEFAULT_URL);
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
        hwnds.add(getDriver().getWindowHandle());
    }

    @AfterClass()
    public void checkHwnds() {
        assertThat(hwnds.size()).isGreaterThan(2);
    }
}
