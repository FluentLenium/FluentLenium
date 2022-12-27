package io.fluentlenium.adapter.sharedwebdriver;

import io.fluentlenium.adapter.SharedMutator;
import io.fluentlenium.adapter.SharedMutator.EffectiveParameters;
import io.fluentlenium.configuration.Configuration;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

/**
 * A singleton container for all running {@link SharedWebDriver} in the JVM.
 * <p>
 * It delegates all calls to a {@link SharedWebdriverSingletonImpl} instance.
 */
public enum SharedWebDriverContainer {
    /**
     * Singleton
     */
    INSTANCE;

    private final SharedWebdriverSingletonImpl impl = new SharedWebdriverSingletonImpl();

    /**
     * Creates a new Shared WebDriver Container.
     */
    SharedWebDriverContainer() {
        // NOPMD SingularField
        SharedWebDriverContainerShutdownHook shutdownHook =
                new SharedWebDriverContainerShutdownHook("SharedWebDriverContainerShutdownHook");
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    public SharedWebDriver getDriver(EffectiveParameters<?> parameters) {
        return impl.getDriver(parameters);
    }

    public SharedWebDriver getOrCreateDriver(Supplier<WebDriver> webDriverFactory, EffectiveParameters<?> parameters) {
        return impl.getOrCreateDriver(webDriverFactory, parameters);
    }

    public List<SharedWebDriver> getAllDrivers() {
        return impl.getAllDrivers();
    }

    public void quit(SharedWebDriver driver) {
        impl.quit(driver);
    }

    public void quitAll() {
        impl.quitAll();
    }

    public List<SharedWebDriver> getTestClassDrivers(Class<?> testClass) {
        return impl.getTestClassDrivers(testClass);
    }

    public SharedWebDriver getSharedWebDriver(SharedMutator.EffectiveParameters<?> parameters,
                                              ExecutorService webDriverExecutor,
                                              Supplier<WebDriver> webDriver,
                                              Configuration configuration)
            throws ExecutionException, InterruptedException {
        return impl.getSharedWebDriver(parameters, webDriverExecutor, webDriver, configuration);
    }

    public WebDriver newWebDriver(String name, Capabilities capabilities, Configuration configuration) {
        return impl.newWebDriver(name, capabilities, configuration);
    }

}
