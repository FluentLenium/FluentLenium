package io.fluentlenium.configuration;

import java.lang.annotation.*;

/**
 * Mark a factory as default factory that can be overriden without throwing an exception.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DefaultFactory {
}
