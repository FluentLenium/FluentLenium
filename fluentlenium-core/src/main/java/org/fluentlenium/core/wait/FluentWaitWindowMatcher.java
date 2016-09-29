package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.fluentlenium.core.FluentControl;

public class FluentWaitWindowMatcher extends AbstractWaitMatcher {

    private FluentWait wait;
    private String windowName;

    protected FluentWaitWindowMatcher(FluentWait wait, String windowName) {
        this.wait = wait;
        this.windowName = windowName;
    }

    public boolean isDisplayed() {
        Predicate<FluentControl> isDisplayed = new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl fluent) {
                return fluent.getDriver().getWindowHandles().contains(windowName);
            }
        };

        until(wait, isDisplayed, String.format("Window %s should be displayed.", windowName));
        return true;
    }

    public boolean isNotDisplayed() {
        Predicate<FluentControl> isNotDisplayed = new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl fluent) {
                return !fluent.getDriver().getWindowHandles().contains(windowName);
            }
        };

        until(wait, isNotDisplayed, String.format("Window %s should not be displayed.", windowName));
        return true;
    }

}
