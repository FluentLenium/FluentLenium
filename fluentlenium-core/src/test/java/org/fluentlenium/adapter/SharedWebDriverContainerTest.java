package org.fluentlenium.adapter;

import com.google.common.base.Supplier;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SharedWebDriverContainerTest implements Supplier<WebDriver> {

    private SharedWebDriverContainer.Impl container;

    @Before
    public void before() {
        container = new SharedWebDriverContainer.Impl();
    }

    @After
    public void after() {
        container.quitAll();
        assertThat(container.getAllDrivers()).isEmpty();
    }

    @Test
    public void getOrCreateDriverWithSameTestNamesCreatesOneInstance() {
        final SharedWebDriver driver = container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.METHOD);

        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);

        final SharedWebDriver driver2 = container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.METHOD);

        assertThat(driver).isEqualTo(driver2);
        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);

        container.quit(driver);

        assertThat(container.getAllDrivers()).isEmpty();
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
    }

    @Test
    public void getOrCreateDriverWithDifferentTestNamesCreatesDistinctInstances() {
        final SharedWebDriver driver = container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.METHOD);

        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);

        final SharedWebDriver driver2 = container.getOrCreateDriver(this, Object.class, "otherTest", DriverLifecycle.METHOD);

        assertThat(driver).isNotEqualTo(driver2);
        assertThat(container.getAllDrivers()).containsOnly(driver, driver2);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver, driver2);

        assertThat(container.getAllDrivers().size()).isEqualTo(2);
        container.quit(driver);
        assertThat(container.getAllDrivers().size()).isEqualTo(1);
        assertThat(container.getAllDrivers().get(0)).isEqualTo(driver2);
        container.quit(driver2);

        assertThat(container.getAllDrivers()).isEmpty();
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
    }

    @Test
    public void getOrCreateDriverWithDifferentTestClassesCreatesDistinctInstances() {
        final SharedWebDriver driver = container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.METHOD);

        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);
        assertThat(container.getTestClassDrivers(String.class)).isEmpty();

        final SharedWebDriver driver2 = container.getOrCreateDriver(this, String.class, "test", DriverLifecycle.METHOD);

        assertThat(driver).isNotEqualTo(driver2);
        assertThat(container.getAllDrivers()).containsOnly(driver, driver2);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);
        assertThat(container.getTestClassDrivers(String.class)).containsOnly(driver2);

        assertThat(container.getAllDrivers().size()).isEqualTo(2);
        container.quit(driver);
        assertThat(container.getAllDrivers().size()).isEqualTo(1);
        assertThat(container.getAllDrivers().get(0)).isEqualTo(driver2);
        container.quit(driver2);

        assertThat(container.getAllDrivers()).isEmpty();
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
        assertThat(container.getTestClassDrivers(String.class)).isEmpty();
    }

    @Test
    public void getOrCreateDriverWithDifferentTestNamesAndStrategyPerClassCreatesOneInstance() {
        final SharedWebDriver driver = container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.CLASS);

        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);

        final SharedWebDriver driver2 = container.getOrCreateDriver(this, Object.class, "otherTest", DriverLifecycle.CLASS);

        assertThat(driver).isEqualTo(driver2);
        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);

        container.quit(driver);

        assertThat(container.getAllDrivers()).isEmpty();
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
    }

    @Test
    public void getOrCreateDriverWithDifferentTestNamesAndDifferentTestClassAndStrategyPerClassCreatesDistinctInstance() {
        final SharedWebDriver driver = container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.CLASS);

        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);
        assertThat(container.getTestClassDrivers(String.class)).isEmpty();

        final SharedWebDriver driver2 = container.getOrCreateDriver(this, String.class, "otherTest", DriverLifecycle.CLASS);

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

    @Test
    public void getOrCreateDriverWithDifferentTestNamesAndDifferentTestClassAndStrategyOnceCreatesOneInstance() {
        final SharedWebDriver driver = container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.JVM);

        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
        assertThat(container.getTestClassDrivers(String.class)).isEmpty();

        final SharedWebDriver driver2 = container.getOrCreateDriver(this, String.class, "otherTest", DriverLifecycle.JVM);

        assertThat(driver).isEqualTo(driver2);
        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
        assertThat(container.getTestClassDrivers(String.class)).isEmpty();

        container.quit(driver);

        assertThat(container.getAllDrivers()).isEmpty();
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
        assertThat(container.getTestClassDrivers(String.class)).isEmpty();
    }

    @Test
    public void quitAllShouldQuitAllDrivers() {
        final SharedWebDriver driver = container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.METHOD);
        final SharedWebDriver driver2 = container.getOrCreateDriver(this, String.class, "test", DriverLifecycle.METHOD);

        final SharedWebDriver driver3 = container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.CLASS);
        final SharedWebDriver driver4 = container.getOrCreateDriver(this, String.class, "otherTest", DriverLifecycle.CLASS);

        final SharedWebDriver driver5 = container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.JVM);
        final SharedWebDriver driver6 = container.getOrCreateDriver(this, String.class, "otherTest", DriverLifecycle.JVM);

        final Set<SharedWebDriver> drivers = new LinkedHashSet<>();
        drivers.add(driver);
        drivers.add(driver2);
        drivers.add(driver3);
        drivers.add(driver4);
        drivers.add(driver5);
        drivers.add(driver6);

        assertThat(container.getAllDrivers()).containsOnly(drivers.toArray(new SharedWebDriver[drivers.size()]));
        assertThat(container.getTestClassDrivers(Object.class)).isNotEmpty();
        assertThat(container.getTestClassDrivers(String.class)).isNotEmpty();

        container.quitAll();

        assertThat(container.getAllDrivers()).isEmpty();
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
        assertThat(container.getTestClassDrivers(String.class)).isEmpty();
    }

    @Test
    public void testSharedDriverBean() {
        final WebDriver webDriver = get();
        final Class<Object> testClass = Object.class;
        final String testName = "test";
        final DriverLifecycle driverLifecycle = DriverLifecycle.METHOD;

        final SharedWebDriver sharedWebDriver = new SharedWebDriver(webDriver, testClass, testName, driverLifecycle);

        assertThat(sharedWebDriver.getDriver()).isSameAs(webDriver);
        assertThat(sharedWebDriver.getWrappedDriver()).isSameAs(webDriver);
        assertThat(sharedWebDriver.getTestClass()).isSameAs(testClass);
        assertThat(sharedWebDriver.getTestName()).isSameAs(testName);
        assertThat(sharedWebDriver.getDriverLifecycle()).isSameAs(driverLifecycle);

        assertThat(sharedWebDriver.toString()).contains(webDriver.toString());
    }

    @Override
    public WebDriver get() {
        return Mockito.mock(WebDriver.class);
    }
}
