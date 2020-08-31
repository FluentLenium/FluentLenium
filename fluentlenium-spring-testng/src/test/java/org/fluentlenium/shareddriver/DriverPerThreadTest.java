package org.fluentlenium.shareddriver;

import org.fluentlenium.IntegrationFluentTestNg;
import org.fluentlenium.configuration.FluentConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.THREAD;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = THREAD)
public class DriverPerThreadTest extends IntegrationFluentTestNg {

    private List<String> hwnds = new ArrayList<>();

    @Test(invocationCount = 4, threadPoolSize = 3)
    public void firstMethod() {
        goTo(IntegrationFluentTestNg.DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
        hwnds.add(getDriver().getWindowHandle());
    }

    @AfterClass()
    public void checkHwnds() {
        assertThat(hwnds.size()).isGreaterThan(2);
    }
}
