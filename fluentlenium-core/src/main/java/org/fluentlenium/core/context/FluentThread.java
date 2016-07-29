package org.fluentlenium.core.context;


import org.fluentlenium.adapter.FluentAdapter;

public final class FluentThread {
    private FluentThread() {
    }

    public static final InheritableThreadLocal<FluentAdapter> userThreadLocal = new InheritableThreadLocal<FluentAdapter>();

    public static void set(FluentAdapter fluent) {
        userThreadLocal.set(fluent);
    }

    public static void unset() {
        userThreadLocal.remove();
    }

    public static FluentAdapter get() {
        return userThreadLocal.get();
    }
}
