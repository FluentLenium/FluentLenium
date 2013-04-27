package org.fluentlenium.adapter.util;

import org.fluentlenium.core.FluentAdapter;

public final class SharedDriverHelper {


    public static SharedDriver getSharedBrowser(final Class classe) {
        Class<?> cls;
        for (cls = classe; FluentAdapter.class.isAssignableFrom(cls); cls = cls.getSuperclass()) {
            if (cls.isAnnotationPresent(SharedDriver.class)) {
                return cls.getAnnotation(SharedDriver.class);
            }
        }
        return null;
    }

    public boolean isSharedDriver(final Class classe) {
        return (getSharedBrowser(classe) != null);
    }

    public static boolean isDeleteCookies(final Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser != null && sharedBrowser.deleteCookies());
    }

    public static boolean isSharedDriverOnce(final Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.ONCE);
    }

    public static boolean isSharedDriverPerClass(final Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.PER_CLASS);
    }

    public static boolean isSharedDriverPerMethod(final Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.PER_METHOD);
    }

    public static boolean isDefaultSharedDriver(final Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser == null);
    }
}