package io.fluentlenium.core.conditions;

import java.lang.annotation.*;

/**
 * Marker annotation for negation of this object.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Negation {
}
