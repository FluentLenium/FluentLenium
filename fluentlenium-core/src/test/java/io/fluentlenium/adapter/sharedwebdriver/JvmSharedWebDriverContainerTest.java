package io.fluentlenium.adapter.sharedwebdriver;

import io.fluentlenium.adapter.SharedMutator;
import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class JvmSharedWebDriverContainerTest implements Supplier<WebDriver> {

    private SharedWebdriverSingletonImpl container;

    @Before
    public void before() {
        container = new SharedWebdriverSingletonImpl();
    }

    @After
    public void after() {
        container.quitAll();
        assertThat(container.getAllDrivers()).isEmpty();
    }

    @Test
    public void getOrCreateDriverWithDifferentTestNamesAndDifferentTestClassAndStrategyOnceCreatesOneInstance() {
        SharedMutator.EffectiveParameters<?> parameters1 = new SharedMutator.EffectiveParameters<>(Object.class, "test", DriverLifecycle.JVM);
        SharedMutator.EffectiveParameters<?> parameters2 = new SharedMutator.EffectiveParameters<>(String.class, "otherTest", DriverLifecycle.JVM);
        SharedWebDriver driver = container.getOrCreateDriver(this, parameters1);
        SharedWebDriver driver2 = container.getOrCreateDriver(this, parameters2);
        assertThat(driver).isEqualTo(driver2);
        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
        assertThat(container.getTestClassDrivers(String.class)).isEmpty();

        container.quit(driver);
        assertThat(container.getAllDrivers()).isEmpty();
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
        assertThat(container.getTestClassDrivers(String.class)).isEmpty();
    }

    @Override
    public WebDriver get() {
        return Mockito.mock(WebDriver.class);
    }
}
