package org.fluentlenium.adapter.sharedwebdriver;

import org.fluentlenium.adapter.SharedMutator.EffectiveParameters;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadSharedWebDriverContainerTest implements Supplier<WebDriver> {

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
    public void getOrCreateDriverWithSameClassSameTestNamesDifferentThreadCreatesDistinctInstances()
            throws ExecutionException, InterruptedException {
        EffectiveParameters<?> parameters = new EffectiveParameters<>(Object.class, "test", DriverLifecycle.THREAD);
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(2);

        CompletableFuture<SharedWebDriver> futureDriver = CompletableFuture.supplyAsync(
                () -> container.getOrCreateDriver(this, parameters), threadPoolExecutor);

        CompletableFuture<SharedWebDriver> futureDriver2 = CompletableFuture.supplyAsync(
                () -> container.getOrCreateDriver(this, parameters), threadPoolExecutor);

        SharedWebDriver driver = futureDriver.get();
        SharedWebDriver driver2 = futureDriver2.get();

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
    public void getOrCreateDriverWithSameClassSameTestNamesSameThreadCreatesOneInstances()
            throws ExecutionException, InterruptedException {
        EffectiveParameters<?> parameters = new EffectiveParameters<>(Object.class, "test", DriverLifecycle.THREAD);
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(1);

        CompletableFuture<SharedWebDriver> futureDriver = CompletableFuture.supplyAsync(
                () -> container.getOrCreateDriver(this, parameters), threadPoolExecutor);

        SharedWebDriver driver = futureDriver.get();
        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);
        assertThat(container.getAllDrivers().size()).isEqualTo(1);

        CompletableFuture<SharedWebDriver> futureDriver2 = CompletableFuture.supplyAsync(
                () -> container.getOrCreateDriver(this, parameters), threadPoolExecutor);

        SharedWebDriver driver2 = futureDriver2.get();

        assertThat(driver).isEqualTo(driver2);
        assertThat(container.getAllDrivers()).containsOnly(driver);
        assertThat(container.getTestClassDrivers(Object.class)).containsOnly(driver);
        assertThat(container.getAllDrivers().size()).isEqualTo(1);

        container.quit(driver);
        assertThat(container.getAllDrivers()).isEmpty();
        assertThat(container.getTestClassDrivers(Object.class)).isEmpty();
    }

    @Test
    public void getOrCreateDriverWithSameThreadDifferentTestClassCreatesDistinctInstances()
            throws ExecutionException, InterruptedException {
        EffectiveParameters<?> parameters1 = new EffectiveParameters<>(Object.class, "test", DriverLifecycle.THREAD);
        EffectiveParameters<?> parameters2 = new EffectiveParameters<>(String.class, "test", DriverLifecycle.THREAD);

        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(1);

        CompletableFuture<SharedWebDriver> futureDriver = CompletableFuture.supplyAsync(
                () -> container.getOrCreateDriver(this, parameters1), threadPoolExecutor);

        CompletableFuture<SharedWebDriver> futureDriver2 = CompletableFuture.supplyAsync(
                () -> container.getOrCreateDriver(this, parameters2), threadPoolExecutor);

        SharedWebDriver driver = futureDriver.get();
        SharedWebDriver driver2 = futureDriver2.get();

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
    }

    @Test
    public void getOrCreateDriverWithSameThreadDifferentTestNameCreatesDistinctInstances()
            throws ExecutionException, InterruptedException {
        EffectiveParameters<?> parameters1 = new EffectiveParameters<>(Object.class, "test", DriverLifecycle.THREAD);
        EffectiveParameters<?> parameters2 = new EffectiveParameters<>(Object.class, "test2", DriverLifecycle.THREAD);

        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(1);

        CompletableFuture<SharedWebDriver> futureDriver = CompletableFuture.supplyAsync(
                () -> container.getOrCreateDriver(this, parameters1), threadPoolExecutor);

        CompletableFuture<SharedWebDriver> futureDriver2 = CompletableFuture.supplyAsync(
                () -> container.getOrCreateDriver(this, parameters2), threadPoolExecutor);

        SharedWebDriver driver = futureDriver.get();
        SharedWebDriver driver2 = futureDriver2.get();

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

    @Override
    public WebDriver get() {
        return Mockito.mock(WebDriver.class);
    }
}
