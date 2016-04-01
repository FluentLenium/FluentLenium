package org.fluentlenium.cucumber.adapter;

import org.fluentlenium.core.Fluent;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.cucumber.adapter.util.SharedDriverHelper;
import org.fluentlenium.cucumber.adapter.util.ShutdownHook;
import org.openqa.selenium.WebDriver;

public class FluentCucumberTest extends FluentAdapter {
    public static boolean isSharedDriverPerScenario;
    public static WebDriver sharedDriver = null;

    protected Fluent initFluentWithWebDriver(final FluentCucumberTest initializer) {
        return initFluentWithWebDriver(initializer, initializer.getDriver());
    }

    protected Fluent initFluentWithWebDriver(final FluentCucumberTest initializer, WebDriver driver) {
        Class clazz = initializer.getClass();

        if (SharedDriverHelper.isSharedDriverPerFeature(clazz)) {
            synchronized (this) {
                if (sharedDriver == null) {
                    initSharedDriver(driver);
                    killTheBrowserOnShutdown();
                } else {
                    initFluentWithExistingDriver();
                }
            }
        } else if (SharedDriverHelper.isSharedDriverPerScenario(clazz) || SharedDriverHelper.isDefaultSharedDriver(clazz)) {
            synchronized (this) {
                if (!isSharedDriverPerScenario) {
                    initSharedDriver(driver);
                    isSharedDriverPerScenario = true;
                } else {
                    initFluentWithExistingDriver();
                }
            }
        } else {
            initFluentFromDefaultDriver();
        }
        return this;
    }

    private void initSharedDriver(WebDriver driver) {
        if (driver != null) {
            sharedDriver = driver;
            initFluentWithExistingDriver();
        } else {
            initFluentFromDefaultDriver();
        }
        sharedDriver = getDriver();
    }

    protected Fluent initFluent() {
        return initFluentWithWebDriver(this);
    }

    @Override
    protected Fluent initFluent(WebDriver driver) {
        if (sharedDriver == null) {
            sharedDriver = driver;
            initFluentWithExistingDriver();
            killTheBrowserOnShutdown();
        } else {
            initFluentWithExistingDriver();
        }
        return this;
    }

    private void initFluentWithExistingDriver() {
        super.initFluent(sharedDriver).withDefaultUrl(getDefaultBaseUrl());
    }

    private void initFluentFromDefaultDriver() {
        super.initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
    }

    @Override
    public void quit() {
        if (isSharedDriverPerScenario) {
            sharedDriver.quit();
            sharedDriver = null;
            isSharedDriverPerScenario = false;
        }
    }

    private void killTheBrowserOnShutdown() {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook("fluentlenium", this));
    }

    protected enum Mode {TAKE_SNAPSHOT_ON_FAIL, NEVER_TAKE_SNAPSHOT;}

}
