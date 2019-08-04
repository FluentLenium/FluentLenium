package org.fluentlenium.adapter.sharedwebdriver;

import org.fluentlenium.adapter.SharedMutator.EffectiveParameters;
import org.openqa.selenium.WebDriver;

import java.util.List;
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

}
