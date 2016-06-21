package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentThread;
import org.fluentlenium.core.filter.Filter;

import java.util.Collections;

public class FluentWaitWindowMatcher {

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

        FluentWaitMatcher.until(wait, isDisplayed, Collections.<Filter>emptyList(),
                WaitMessage.isWindowDisplayedMessage(windowName));
        return FluentThread.get();
    }

    public Fluent isNotDisplayed() {
        Predicate<Fluent> isNotDisplayed = new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent fluent) {
                return !fluent.getDriver().getWindowHandles().contains(windowName);
            }
        };

        FluentWaitMatcher.until(wait, isNotDisplayed, Collections.<Filter>emptyList(),
                WaitMessage.isWindowNotDisplayedMessage(windowName));
        return FluentThread.get();
    }

}
