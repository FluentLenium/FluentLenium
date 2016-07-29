package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.fluentlenium.core.FluentDriver;

public class FluentWaitWindowMatcher extends AbstractWaitMatcher {

    private FluentWait wait;
    private String windowName;

    protected FluentWaitWindowMatcher(FluentWait wait, String windowName) {
        this.wait = wait;
        this.windowName = windowName;
    }

    public boolean isDisplayed() {
        Predicate<FluentDriver> isDisplayed = new Predicate<FluentDriver>() {
            @Override
            public boolean apply(FluentDriver fluent) {
                return fluent.getDriver().getWindowHandles().contains(windowName);
            }
        };

        until(wait, isDisplayed, FluentWaitMessages.isWindowDisplayedMessage(windowName));
        return true;
    }

    public boolean isNotDisplayed() {
        Predicate<FluentDriver> isNotDisplayed = new Predicate<FluentDriver>() {
            @Override
            public boolean apply(FluentDriver fluent) {
                return !fluent.getDriver().getWindowHandles().contains(windowName);
            }
        };

        until(wait, isNotDisplayed, FluentWaitMessages.isWindowNotDisplayedMessage(windowName));
        return true;
    }

}
