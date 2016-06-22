package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentThread;

public class FluentWaitWindowMatcher extends AbstractWaitMatcher {

    private FluentWait wait;
    private String windowName;

    public FluentWaitWindowMatcher(FluentWait wait, String windowName) {
        this.wait = wait;
        this.windowName = windowName;
    }

    public Fluent isDisplayed() {
        Predicate<Fluent> isDisplayed = new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent fluent) {
                return fluent.getDriver().getWindowHandles().contains(windowName);
            }
        };

        until(wait, isDisplayed, FluentWaitMessages.isWindowDisplayedMessage(windowName));
        return FluentThread.get();
    }

    public Fluent isNotDisplayed() {
        Predicate<Fluent> isNotDisplayed = new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent fluent) {
                return !fluent.getDriver().getWindowHandles().contains(windowName);
            }
        };

        until(wait, isNotDisplayed, FluentWaitMessages.isWindowNotDisplayedMessage(windowName));
        return FluentThread.get();
    }

}
