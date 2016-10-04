package org.fluentlenium.core.hook;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Hook(NanoHook.class)
@HookOptions(NanoHookOptions.class)
public @interface NanoHookAnnotation {
    String value();
}
