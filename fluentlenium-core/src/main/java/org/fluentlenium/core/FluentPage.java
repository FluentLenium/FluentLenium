package org.fluentlenium.core;

import lombok.experimental.Delegate;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.context.FluentThread;

/**
 * Use the Page Object Pattern to have more resilient tests.
 */
public abstract class FluentPage implements FluentPageControl {

    @Delegate
    private FluentControl support;

    protected FluentPage() {
        super();
        support = FluentThread.get();
    }

    @Override
    public String getUrl() {
        if (this.getClass().isAnnotationPresent(PageUrl.class)) {
            String url = this.getClass().getAnnotation(PageUrl.class).value();
            if (!url.isEmpty()) {
                return url;
            }
        }
        return null;
    }

    @Override
    public void isAt() {
    }

    @Override
    public final void go() {
        goTo(getUrl());
    }
}
