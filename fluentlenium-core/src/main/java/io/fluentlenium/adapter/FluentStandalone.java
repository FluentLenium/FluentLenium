package io.fluentlenium.adapter;

/**
 * Create an instance of this class if you want to use FluentLenium as an automation framework only.
 * <p>
 * You have to manually invoke {@link #init()} to initialize the WebDriver, and {@link #quit()} to close it.
 */
public class FluentStandalone extends FluentAdapter {
    /**
     * Initialize Fluent WebDriver.
     */
    public void init() {
        initFluent(newWebDriver());
    }

    /**
     * Close Fluent WebDriver.
     */
    public void quit() {
        if (getDriver() != null) {
            getDriver().quit();
        }
        releaseFluent();
    }
}
