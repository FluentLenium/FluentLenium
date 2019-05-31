package org.fluentlenium.adapter.sharedwebdriver;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassSharedWebDriverContainerTest implements Supplier<WebDriver> {

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
    public void getOrCreateDriverWithDifferentTestNamesAndStrategyPerClassCreatesOneInstance() {
        SharedWebDriver driver = container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.CLASS);
        SharedWebDriver driver2 = container.getOrCreateDriver(this, Object.class, "otherTest", DriverLifecycle.CLASS);
        assertThat(driver).isEqualTo(driver2);
        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);

        container.quit(driver);
        assertThat(container.getAllDrivers()).isEmpty();
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
    }

    @Test
    public void getOrCreateDriverWithDifferentTestNamesAndDifferentTestClassAndStrategyPerClassCreatesDistinctInstance() {
        SharedWebDriver driver = container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.CLASS);
        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);
        assertThat(container.getTestClassDrivers(String.class)).isEmpty();

        SharedWebDriver driver2 = container.getOrCreateDriver(this, String.class, "otherTest", DriverLifecycle.CLASS);
        assertThat(driver).isNotEqualTo(driver2);
        assertThat(container.getAllDrivers()).containsOnly(driver, driver2);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);
        assertThat(container.getTestClassDrivers(String.class)).containsOnly(driver2);
        assertThat(container.getAllDrivers().size()).isEqualTo(2);

        container.quit(driver2);
        assertThat(container.getAllDrivers().size()).isEqualTo(1);
        assertThat(container.getAllDrivers().get(0)).isEqualTo(driver);

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
