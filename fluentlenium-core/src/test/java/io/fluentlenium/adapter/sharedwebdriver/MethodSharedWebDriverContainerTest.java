package io.fluentlenium.adapter.sharedwebdriver;

import io.fluentlenium.adapter.SharedMutator;import io.fluentlenium.adapter.SharedMutator.EffectiveParameters;
import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodSharedWebDriverContainerTest implements Supplier<WebDriver> {

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
    public void getOrCreateDriverWithSameTestNamesCreatesOneInstance() {
        SharedMutator.EffectiveParameters<?> parameters1 = new SharedMutator.EffectiveParameters<>(Object.class, "test", DriverLifecycle.METHOD);
        SharedMutator.EffectiveParameters<?> parameters2 = new SharedMutator.EffectiveParameters<>(Object.class, "test", DriverLifecycle.METHOD);
        SharedWebDriver driver = container.getOrCreateDriver(this, parameters1);
        SharedWebDriver driver2 = container.getOrCreateDriver(this, parameters2);

        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);
        assertThat(driver).isEqualTo(driver2);
        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);

        container.quit(driver);
        assertThat(container.getAllDrivers()).isEmpty();
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
    }

    @Test
    public void getOrCreateDriverWithDifferentTestNamesCreatesDistinctInstances() {
        SharedMutator.EffectiveParameters<?> parameters1 = new SharedMutator.EffectiveParameters<>(Object.class, "test", DriverLifecycle.METHOD);
        SharedMutator.EffectiveParameters<?> parameters2 = new SharedMutator.EffectiveParameters<>(Object.class, "otherTest", DriverLifecycle.METHOD);
        SharedWebDriver driver = container.getOrCreateDriver(this, parameters1);
        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);

        SharedWebDriver driver2 = container.getOrCreateDriver(this, parameters2);
        assertThat(driver).isNotEqualTo(driver2);
        assertThat(container.getAllDrivers()).containsOnly(driver, driver2);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver, driver2);
        assertThat(container.getAllDrivers()).hasSize(2);

        container.quit(driver);
        assertThat(container.getAllDrivers()).hasSize(1);
        assertThat(container.getAllDrivers().get(0)).isEqualTo(driver2);

        container.quit(driver2);
        assertThat(container.getAllDrivers()).isEmpty();
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
    }

    @Test
    public void getOrCreateDriverWithDifferentTestClassesCreatesDistinctInstances() {
        SharedMutator.EffectiveParameters<?> parameters1 = new SharedMutator.EffectiveParameters<>(Object.class, "test", DriverLifecycle.METHOD);
        SharedMutator.EffectiveParameters<?> parameters2 = new SharedMutator.EffectiveParameters<>(String.class, "test", DriverLifecycle.METHOD);
        SharedWebDriver driver = container.getOrCreateDriver(this, parameters1);
        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);
        assertThat(container.getTestClassDrivers(String.class)).isEmpty();

        SharedWebDriver driver2 = container.getOrCreateDriver(this, parameters2);
        assertThat(driver).isNotEqualTo(driver2);
        assertThat(container.getAllDrivers()).containsOnly(driver, driver2);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);
        assertThat(container.getTestClassDrivers(String.class)).containsOnly(driver2);
        assertThat(container.getAllDrivers()).hasSize(2);

        container.quit(driver);
        assertThat(container.getAllDrivers()).hasSize(1);
        assertThat(container.getAllDrivers().get(0)).isEqualTo(driver2);

        container.quit(driver2);
        assertThat(container.getAllDrivers()).isEmpty();
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
        assertThat(container.getTestClassDrivers(String.class)).isEmpty();
    }

    @Override
    public WebDriver get() {
        return Mockito.mock(WebDriver.class);
    }
}
