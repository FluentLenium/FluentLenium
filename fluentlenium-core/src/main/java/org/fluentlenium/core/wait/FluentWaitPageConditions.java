package org.fluentlenium.core.wait;

import java.util.function.Predicate;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

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
    protected FluentWaitPageConditions(final FluentWait wait, final WebDriver driver) {
        this.wait = wait;
        this.webDriver = driver;
    }

    /**
     * Creates a new page wait conditions.
     *
     * @param wait   underlying wait
     * @param driver driver
     * @param page   page to wait for
     */
    protected FluentWaitPageConditions(final FluentWait wait, final WebDriver driver, final FluentPage page) {
        this.wait = wait;
        this.webDriver = driver;
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
            final Predicate<FluentControl> isLoaded = fluent -> {
                final Object result = fluent.executeScript("if (document.readyState) return document.readyState;")
                        .getStringResult();
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
        final Predicate<FluentControl> isLoaded = fluent -> {
            try {
                page.isAt();
            } catch (final Error e) {
                return false;
            }
            return true;
        };
        until(wait, isLoaded, "");
        return true;
    }
}
