package org.fluentlenium.adapter.util;


public final class SharedDriverHelper {

    public boolean isSharedDriver(final Class<?> classe) {
        return (classe.getAnnotation(SharedDriver.class) != null);
    }

    public static boolean isDeleteCookies(final Class<?> classe) {
        SharedDriver sharedBrowser = classe.getAnnotation(SharedDriver.class);
        return (sharedBrowser != null && sharedBrowser.deleteCookies());
    }

    public static boolean isSharedDriverOnce(final Class<?> classe) {
        SharedDriver sharedBrowser = classe.getAnnotation(SharedDriver.class);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.ONCE);
    }

    public static boolean isSharedDriverPerClass(final Class<?> classe) {
        SharedDriver sharedBrowser = classe.getAnnotation(SharedDriver.class);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.PER_CLASS);
    }

    public static boolean isSharedDriverPerMethod(final Class<?> classe) {
        SharedDriver sharedBrowser = classe.getAnnotation(SharedDriver.class);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.PER_METHOD);
    }

    public static boolean isDefaultSharedDriver(final Class<?> classe) {
        SharedDriver sharedBrowser = classe.getAnnotation(SharedDriver.class);
        return (sharedBrowser == null);
    }
}
