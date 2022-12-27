package io.fluentlenium.core.wait;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.function.Predicate;

/**
 * Page wait conditions
 */
public class FluentWaitPageConditions extends BaseWaitConditions {
    private final FluentWait wait;
    private final WebDriver webDriver;
    private FluentPage page;

    /**
     * Creates a new page wait conditions.
     *
     * @param wait   underlying wait
     * @param driver driver
     */
    protected FluentWaitPageConditions(FluentWait wait, WebDriver driver) {
        this.wait = wait;
        webDriver = driver;
    }

    /**
     * Creates a new page wait conditions.
     *
     * @param wait   underlying wait
     * @param driver driver
     * @param page   page to wait for
     */
    protected FluentWaitPageConditions(FluentWait wait, WebDriver driver, FluentPage page) {
        this.wait = wait;
        webDriver = driver;
        this.page = page;
    }

    /**
     * Check if the current browser page is loaded.
     * <p>
     * Requires javascript to be enabled. Throw an UnsupportedOperationException if not.
     *
     * @return true
     */
    public boolean isLoaded() {
        if (webDriver instanceof JavascriptExecutor) {
            Predicate<FluentControl> isLoaded = fluent -> {
                Object result = fluent.executeScript("if (document.readyState) return document.readyState;").getStringResult();
                return result != null && "complete".equals(result);
            };
            until(wait, isLoaded, String.format("Page %s should be loaded.", webDriver.getCurrentUrl()));
        } else {
            throw new UnsupportedOperationException("Driver must support javascript execution to use this feature");
        }
        return true;
    }

    /**
     * Check if browser is on the page.
     *
     * @return true
     */
    public boolean isAt() {
        if (page == null) {
            throw new IllegalArgumentException(
                    "You should use a page argument when you call the untilPage method to specify the page you want to be. "
                            + "Example : await().untilPage(myPage).isAt();");
        }
        Predicate<FluentControl> isLoaded = fluent -> {
            try {
                page.isAt();
            } catch (Error e) {
                return false;
            }
            return true;
        };
        until(wait, isLoaded, "");
        return true;
    }
}
