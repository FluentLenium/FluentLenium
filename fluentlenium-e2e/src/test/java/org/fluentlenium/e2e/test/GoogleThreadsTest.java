package org.fluentlenium.e2e.test;

import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.FluentConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@FluentConfiguration(driverLifecycle = ConfigurationProperties.DriverLifecycle.THREAD)
public class GoogleThreadsTest extends E2ETest {

    @Test(invocationCount = 5, threadPoolSize = 5)
    public void firstMethod() {
        goTo("http://www.google.com");
        await().atMost(2, TimeUnit.SECONDS).until($(".gsfi")).present();
    }

    @AfterMethod
    public void afterMethod() {
        Assertions.assertThat(window().title()).isEqualTo("Google");
    }
}
