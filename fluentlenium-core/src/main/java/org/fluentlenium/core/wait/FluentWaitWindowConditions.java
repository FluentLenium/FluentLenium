package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.fluentlenium.core.FluentControl;

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
    protected FluentWaitWindowConditions(final FluentWait wait, final String windowName) {
        this.wait = wait;
        this.windowName = windowName;
    }

    /**
     * Check if the window is displayed.
     *
     * @return true
     */
    public boolean displayed() {
        final Predicate<FluentControl> displayed = new Predicate<FluentControl>() {
            @Override
            public boolean apply(final FluentControl fluent) {
                return fluent.getDriver().getWindowHandles().contains(windowName);
            }
        };

        until(wait, displayed, String.format("Window %s should be displayed.", windowName));
        return true;
    }

    /**
     * Check if the window is not displayed.
     *
     * @return true
     */
    public boolean notDisplayed() {
        final Predicate<FluentControl> notDisplayed = new Predicate<FluentControl>() {
            @Override
            public boolean apply(final FluentControl fluent) {
                return !fluent.getDriver().getWindowHandles().contains(windowName);
            }
        };

        until(wait, notDisplayed, String.format("Window %s should not be displayed.", windowName));
        return true;
    }

}
