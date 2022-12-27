package io.fluentlenium.core.hook;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to declared a hook on a test adapter, container or element field.
 */
@Inherited
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Hook {
    /**
     * Hook class.
     *
     * @return hook class
     */
    Class<? extends FluentHook<?>> value();
}
