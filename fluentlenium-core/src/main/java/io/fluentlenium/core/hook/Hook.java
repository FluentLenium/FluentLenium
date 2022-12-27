package io.fluentlenium.core.hook;

import java.lang.annotation.*;

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
