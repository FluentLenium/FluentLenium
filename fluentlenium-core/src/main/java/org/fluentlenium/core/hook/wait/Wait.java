package org.fluentlenium.core.hook.wait;

import org.fluentlenium.core.hook.Hook;
import org.fluentlenium.core.hook.HookOptions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Annotation to enable the Wait Hook.
 *
 * @see WaitHook
 * @see WaitHookOptions
 */
@Inherited
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Hook(WaitHook.class)
@HookOptions(WaitHookOptions.class)
public @interface Wait {
    long timeout() default -1L;

    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    long pollingInterval() default -1L;

    TimeUnit pollingTimeUnit() default TimeUnit.MILLISECONDS;

    boolean withNoDefaultsException() default false;

    java.lang.Class<? extends Throwable>[] ignoreAll() default {};
}
