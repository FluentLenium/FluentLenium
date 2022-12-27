package io.fluentlenium.core.hook;

import java.lang.annotation.*;

/**
 * Annotation used to declared hook options.
 */
@Inherited
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HookOptions {
    /**
     * Hook options class.
     *
     * @return hook options class
     */
    Class<?> value();
}
