package org.fluentlenium.adapter.sharedwebdriver;

import com.google.common.collect.ImmutableSet;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class SharedWebDriverContainerTest implements Supplier<WebDriver> {

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
    public void quitAllShouldQuitAllDrivers() throws ExecutionException, InterruptedException {
        SharedWebDriver driver = container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.METHOD);
        SharedWebDriver driver2 = container.getOrCreateDriver(this, String.class, "test", DriverLifecycle.METHOD);

        SharedWebDriver driver3 = container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.CLASS);
        SharedWebDriver driver4 = container.getOrCreateDriver(this, String.class, "otherTest", DriverLifecycle.CLASS);

        SharedWebDriver driver5 = container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.JVM);
        SharedWebDriver driver6 = container.getOrCreateDriver(this, String.class, "otherTest", DriverLifecycle.JVM);

        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(2);

        CompletableFuture<SharedWebDriver> futureDriver7 = CompletableFuture.supplyAsync(
                () -> container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.THREAD), threadPoolExecutor);

        CompletableFuture<SharedWebDriver> futureDriver8 = CompletableFuture.supplyAsync(
                () -> container.getOrCreateDriver(this, Object.class, "test", DriverLifecycle.THREAD), threadPoolExecutor);

        SharedWebDriver driver7 = futureDriver7.get();
        SharedWebDriver driver8 = futureDriver8.get();

        Set<SharedWebDriver> drivers = ImmutableSet.of(driver, driver2, driver3, driver4, driver5, driver6, driver7, driver8);

        assertThat(container.getAllDrivers()).containsOnly(drivers.toArray(new SharedWebDriver[0]));
        assertThat(container.getTestClassDrivers(Object.class)).isNotEmpty();
        assertThat(container.getTestClassDrivers(String.class)).isNotEmpty();

        container.quitAll();

        assertThat(container.getAllDrivers()).isEmpty();
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
        assertThat(container.getTestClassDrivers(String.class)).isEmpty();
    }

    @Test
    public void testSharedDriverBean() {
        WebDriver webDriver = get();
        Class<Object> testClass = Object.class;
        String testName = "test";
        DriverLifecycle driverLifecycle = DriverLifecycle.METHOD;

        SharedWebDriver sharedWebDriver = new SharedWebDriver(webDriver, testClass, testName, driverLifecycle);

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
