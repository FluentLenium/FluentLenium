package org.fluentlenium.core.hook.wait;

import org.fluentlenium.core.hook.Hook;
import org.fluentlenium.core.hook.HookOptions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Inherited
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Hook(WaitHook.class)
@HookOptions(WaitHookOptions.class)
public @interface Wait {
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
    long atMost() default -1L;
    long pollingEvery() default -1L;
    boolean withNoDefaultsException();
    java.lang.Class<? extends Throwable>[] ignoreAll();
}
