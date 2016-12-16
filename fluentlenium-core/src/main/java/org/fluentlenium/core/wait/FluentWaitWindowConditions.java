package org.fluentlenium.core.wait;

import org.fluentlenium.core.FluentControl;

import java.util.function.Predicate;

/**
 * Window wait conditions.
 */
public class FluentWaitWindowConditions extends BaseWaitConditions {

    private final FluentWait wait;
    private final String windowName;

    /**
     * Creates a new window wait conditions.
     *
     * @param wait       underlying wait
     * @param windowName window name
     */
    protected FluentWaitWindowConditions(FluentWait wait, String windowName) {
        this.wait = wait;
        this.windowName = windowName;
    }

    /**
     * Check if the window is displayed.
     *
     * @return true
     */
    public boolean displayed() {
        Predicate<FluentControl> displayed = fluent -> fluent.getDriver().getWindowHandles().contains(windowName);

        until(wait, displayed, String.format("Window %s should be displayed.", windowName));
        return true;
    }

    /**
     * Check if the window is not displayed.
     *
     * @return true
     */
    public boolean notDisplayed() {
        Predicate<FluentControl> notDisplayed = fluent -> !fluent.getDriver().getWindowHandles().contains(windowName);

        until(wait, notDisplayed, String.format("Window %s should not be displayed.", windowName));
        return true;
    }

}
