package org.fluentlenium.core.proxy.plugin.listelement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListElementAccessor {
    boolean index() default false;
    boolean first() default false;
    boolean last() default false;
}
