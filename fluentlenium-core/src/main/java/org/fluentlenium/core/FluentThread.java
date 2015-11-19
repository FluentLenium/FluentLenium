package org.fluentlenium.core;


public class FluentThread {
    public static final InheritableThreadLocal<Fluent> userThreadLocal = new InheritableThreadLocal<Fluent>();

    public static void set(Fluent fluent) {
        userThreadLocal.set(fluent);
    }

    public static void unset() {
        userThreadLocal.remove();
    }

    public static Fluent get() {
        return userThreadLocal.get();
    }
}
