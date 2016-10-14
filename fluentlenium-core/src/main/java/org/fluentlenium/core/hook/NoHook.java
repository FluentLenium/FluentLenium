package org.fluentlenium.core.hook;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Remove current hook definitions from the container context.
 */
@Inherited
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoHook {
    /**
     * Hook annotations to remove.
     * <p>
     * If no annotation is provided, all hook will be removed.
     *
     * @return Hook annotations to remove
     */
    Hook[] value() default {};
}
