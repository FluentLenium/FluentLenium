package org.fluentlenium.core.context;


import org.fluentlenium.core.FluentDriver;

public final class FluentThread {
    private FluentThread() {
    }

    public static final InheritableThreadLocal<FluentDriver> userThreadLocal = new InheritableThreadLocal<FluentDriver>();

    public static void set(FluentDriver fluent) {
        userThreadLocal.set(fluent);
    }

    public static void unset() {
        userThreadLocal.remove();
    }

    public static FluentDriver get() {
        return userThreadLocal.get();
    }
}
