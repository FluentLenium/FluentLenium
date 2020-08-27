package org.fluentlenium.adapter.sharedwebdriver;

import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.adapter.SharedMutator.EffectiveParameters;
import org.fluentlenium.configuration.Configuration;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import static org.fluentlenium.utils.ExecutorServiceUtil.getExecutor;
import static org.fluentlenium.utils.ExecutorServiceUtil.shutDownExecutor;

/**
 * Shared web driver container singleton implementation.
 */
class SharedWebdriverSingletonImpl {

    private final ClassDriver classDriverImpl = new ClassDriver();
    private final JvmDriver jvmDriverImpl = new JvmDriver();
    private final ThreadDriver threadDriverImpl = new ThreadDriver();
    private final MethodDriver methodDriverImpl = new MethodDriver();
    private final Map<DriverLifecycle, FluentLeniumDriver> drivers;

    SharedWebdriverSingletonImpl() {
        this.drivers = initDrivers();
    }

    private Map<DriverLifecycle, FluentLeniumDriver> initDrivers() {
        Map<DriverLifecycle, FluentLeniumDriver> lifecycleDrivers = new HashMap<>();
        lifecycleDrivers.put(DriverLifecycle.JVM, jvmDriverImpl);
        lifecycleDrivers.put(DriverLifecycle.THREAD, threadDriverImpl);
        lifecycleDrivers.put(DriverLifecycle.CLASS, classDriverImpl);
        lifecycleDrivers.put(DriverLifecycle.METHOD, methodDriverImpl);
        lifecycleDrivers.put(DriverLifecycle.DEFAULT, methodDriverImpl);
        return lifecycleDrivers;
    }

    /**
     * Get an existing or create a new shared driver for the given test, with the given shared driver
     * lifecycle strategy.
     *
     * @param webDriverFactory Supplier supplying new WebDriver instances
     * @param parameters       test parameters
     * @return shared web driver
     */
    SharedWebDriver getOrCreateDriver(Supplier<WebDriver> webDriverFactory, EffectiveParameters<?> parameters) {
        synchronized (this) {
            return Optional.ofNullable(getDriver(parameters))
                    .orElseGet(() -> createAndRegisterNewDriver(webDriverFactory, parameters));
        }
    }

    private SharedWebDriver createAndRegisterNewDriver(Supplier<WebDriver> webDriverFactory, EffectiveParameters<?> parameters) {
        SharedWebDriver driver = createDriver(webDriverFactory, parameters);
        registerDriver(driver);
        return driver;
    }

    private SharedWebDriver createDriver(Supplier<WebDriver> webDriverFactory, EffectiveParameters<?> parameters) {
        WebDriver webDriver = webDriverFactory.get();
        return new SharedWebDriver(webDriver,
                parameters.getTestClass(), parameters.getTestName(), parameters.getDriverLifecycle());
    }

    private void registerDriver(SharedWebDriver driver) {
        synchronized (this) {
            drivers.get(driver.getDriverLifecycle()).addDriver(driver);
        }
    }

    /**
     * Get the current driver for given test class.
     *
     * @param parameters test parameters
     * @return shared WebDriver
     */
    public SharedWebDriver getDriver(EffectiveParameters<?> parameters) {
        synchronized (this) {
            switch (parameters.getDriverLifecycle()) {
                case JVM:
                    return jvmDriverImpl.getDriver();
                case CLASS:
                    return classDriverImpl.getDriver(parameters.getTestClass());
                case THREAD:
                    return threadDriverImpl.getDriver(parameters.getTestClass(), parameters.getTestName(),
                            Thread.currentThread().getId());

                default:
                    return methodDriverImpl.getDriver(parameters.getTestClass(), parameters.getTestName());
            }
        }
    }

    /**
     * Quit an existing shared driver.
     *
     * @param driver Shared WebDriver
     */
    public void quit(SharedWebDriver driver) {
        synchronized (this) {
            drivers.get(driver.getDriverLifecycle()).quitDriver(driver);
        }
    }

    /**
     * Get all WebDriver of this container.
     *
     * @return List of {@link SharedWebDriver}
     */
    List<SharedWebDriver> getAllDrivers() {
        List<SharedWebDriver> allDrivers = new ArrayList<>();
        synchronized (this) {
            Optional.ofNullable(jvmDriverImpl.getDriver()).ifPresent(allDrivers::add);
            allDrivers.addAll(classDriverImpl.getClassDrivers().values());
            allDrivers.addAll(threadDriverImpl.getThreadDrivers().values());
            allDrivers.addAll(methodDriverImpl.getMethodDrivers().values());
        }
        return Collections.unmodifiableList(allDrivers);
    }

    /**
     * Get all shared WebDriver of this container for a given test class.
     *
     * @param testClass test class
     * @return list of shared WebDriver
     */
    List<SharedWebDriver> getTestClassDrivers(Class<?> testClass) {
        List<SharedWebDriver> testClassDrivers = new ArrayList<>();

        synchronized (this) {
            Optional.ofNullable(classDriverImpl.getClassDrivers().get(testClass)).ifPresent(testClassDrivers::add);
            testClassDrivers.addAll(getDrivers(testClass, methodDriverImpl.getMethodDrivers()));
            testClassDrivers.addAll(getDrivers(testClass, threadDriverImpl.getThreadDrivers()));
            return Collections.unmodifiableList(testClassDrivers);
        }
    }

    private List<SharedWebDriver> getDrivers(Class<?> testClass, Map<?, SharedWebDriver> webDrivers) {
        List<SharedWebDriver> sharedDrivers = new ArrayList<>();
        for (SharedWebDriver testDriver : webDrivers.values()) {
            if (testDriver.getTestClass() == testClass) {
                sharedDrivers.add(testDriver);
            }
        }
        return sharedDrivers;
    }

    /**
     * Quit all shared web driver.
     */
    void quitAll() {
        synchronized (this) {
            Optional.ofNullable(jvmDriverImpl.getDriver()).ifPresent(jvmDriverImpl::quitDriver);
            quitAllDrivers(classDriverImpl.getClassDrivers());
            quitAllDrivers(methodDriverImpl.getMethodDrivers());
            quitAllDrivers(threadDriverImpl.getThreadDrivers());
        }
    }

    private void quitAllDrivers(Map<?, SharedWebDriver> driverType) {
        Iterator<SharedWebDriver> testThreadDriversIterator = driverType.values().iterator();
        while (testThreadDriversIterator.hasNext()) {
            testThreadDriversIterator.next().getDriver().quit();
            testThreadDriversIterator.remove();
        }
    }

    /**
     * Returns SharedDriver instance
     *
     * @param parameters        driver parameters
     * @param webDriverExecutor executor service
     * @return SharedDriver
     * @throws ExecutionException   execution exception
     * @throws InterruptedException interrupted exception
     */
    public SharedWebDriver getSharedWebDriver(SharedMutator.EffectiveParameters<?> parameters,
                                              ExecutorService webDriverExecutor,
                                              Supplier<WebDriver> webDriver,
                                              Configuration configuration)
            throws ExecutionException, InterruptedException {
        SharedWebDriver sharedWebDriver = null;
        ExecutorService executorService = getExecutor(webDriverExecutor);

        Integer browserTimeoutRetries = configuration.getBrowserTimeoutRetries();
        for (int retryCount = 0; retryCount < browserTimeoutRetries; retryCount++) {

            Future<SharedWebDriver> futureWebDriver = createDriver(parameters, executorService, webDriver);
            shutDownExecutor(executorService, configuration.getBrowserTimeout());

            try {
                sharedWebDriver = futureWebDriver.get();
            } catch (InterruptedException | ExecutionException e) {
                executorService.shutdownNow();
                throw e;
            }

            if (sharedWebDriver != null) {
                break;
            }
        }

        return sharedWebDriver;
    }

    private Future<SharedWebDriver> createDriver(SharedMutator.EffectiveParameters<?> parameters,
                                                 ExecutorService executorService,
                                                 Supplier<WebDriver> newWebDriver) {
        return executorService.submit(
                () -> SharedWebDriverContainer.INSTANCE.getOrCreateDriver(newWebDriver, parameters));
    }
}
