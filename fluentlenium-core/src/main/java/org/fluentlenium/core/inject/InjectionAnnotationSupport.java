package org.fluentlenium.core.inject;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.hook.Hook;
import org.fluentlenium.core.hook.HookOptions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Utility methods for validating annotation presence.
 */
final class InjectionAnnotationSupport {

    private InjectionAnnotationSupport() {
        //Util class
    }

    /**
     * Checks if the {@link Parent} annotation is present on the argument field.
     *
     * @param field the field to check Parent on
     * @return true if the annotation is present, false otherwise
     */
    static boolean isParent(Field field) {
        return field.isAnnotationPresent(Parent.class);
    }

    /**
     * Checks if the {@link Page} annotation is present on the argument field.
     *
     * @param field the field to check Page on
     * @return true if the annotation is present, false otherwise
     */
    static boolean isContainer(Field field) {
        return field.isAnnotationPresent(Page.class);
    }

    /**
     * Checks if the {@link NoInject} annotation is present on the argument field.
     *
     * @param field the field to check NoInject on
     * @return true if the annotation is present, false otherwise
     */
    static boolean isNoInject(Field field) {
        return field.isAnnotationPresent(NoInject.class);
    }

    /**
     * Checks if the {@link Hook} annotation is present on the type of the argument annotation.
     *
     * @param annotation the annotation to check Hook on
     * @return true if the annotation is present, false otherwise
     */
    static boolean isAnnotationTypeHook(Annotation annotation) {
        return annotation.annotationType().isAnnotationPresent(Hook.class);
    }

    /**
     * Checks if the {@link HookOptions} annotation is present on the type of the argument annotation.
     *
     * @param annotation the annotation to check the HookOptions on
     * @return true if the annotation is present, false otherwise
     */
    static boolean isAnnotationTypeHookOptions(Annotation annotation) {
        return annotation.annotationType().isAnnotationPresent(HookOptions.class);
    }
}
