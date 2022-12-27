package io.fluentlenium.core.hook;

import java.lang.annotation.*;

@Inherited
@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Hook(NanoHook.class)
@HookOptions(NanoHookOptions.class)
public @interface NanoHookAnnotation {
    String value();
}
