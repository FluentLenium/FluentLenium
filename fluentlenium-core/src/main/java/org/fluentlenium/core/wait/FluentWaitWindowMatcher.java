package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.fluentlenium.core.FluentControl;

public class FluentWaitWindowMatcher extends BaseWaitMatcher {

    private final FluentWait wait;
    private final String windowName;

    protected FluentWaitWindowMatcher(final FluentWait wait, final String windowName) {
        this.wait = wait;
        this.windowName = windowName;
    }

    public boolean isDisplayed() {
        final Predicate<FluentControl> isDisplayed = new Predicate<FluentControl>() {
            @Override
            public boolean apply(final FluentControl fluent) {
                return fluent.getDriver().getWindowHandles().contains(windowName);
            }
        };

        until(wait, isDisplayed, String.format("Window %s should be displayed.", windowName));
        return true;
    }

    public boolean isNotDisplayed() {
        final Predicate<FluentControl> isNotDisplayed = new Predicate<FluentControl>() {
            @Override
            public boolean apply(final FluentControl fluent) {
                return !fluent.getDriver().getWindowHandles().contains(windowName);
            }
        };

        until(wait, isNotDisplayed, String.format("Window %s should not be displayed.", windowName));
        return true;
    }

}
