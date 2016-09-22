package org.fluentlenium.core.wait;

import com.google.common.base.Joiner;

import java.util.List;

final class FluentWaitMessages {
    static final String isPageLoaded(String url) {
        return String.format("Page %s is not loaded.", url);
    }

    static final String isWindowDisplayedMessage(String windowName) {
        return String.format("Window %s is not displayed.", windowName);
    }

    static final String isWindowNotDisplayedMessage(String windowName) {
        return String.format("Window %s is displayed.", windowName);
    }

}

