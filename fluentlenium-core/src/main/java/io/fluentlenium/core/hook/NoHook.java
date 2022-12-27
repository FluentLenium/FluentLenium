package io.fluentlenium.core.hook;

import java.lang.annotation.*;

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
