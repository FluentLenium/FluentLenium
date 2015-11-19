package org.fluentlenium.cucumber.adapter.util;

import org.fluentlenium.cucumber.adapter.FluentCucumberTest;

public final class SharedDriverHelper {


    public static SharedDriver getSharedBrowser(final Class classe) {
        Class<?> cls;
        for (cls = classe; FluentCucumberTest.class.isAssignableFrom(cls); cls = cls.getSuperclass()) {
            if (cls.isAnnotationPresent(SharedDriver.class)) {
                return cls.getAnnotation(SharedDriver.class);
            }
        }
        return null;
    }

    public boolean isSharedDriver(final Class classe) {
        return (getSharedBrowser(classe) != null);
    }

    public static boolean isSharedDriverPerFeature(final Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.PER_FEATURE);
    }

    public static boolean isSharedDriverPerScenario(final Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.PER_SCENARIO);
    }

    public static boolean isDefaultSharedDriver(final Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser == null);
    }
}
