package org.fluentlenium.core;


public class FluentThread {
        public static final ThreadLocal<Fluent> userThreadLocal = new ThreadLocal<Fluent>();

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
