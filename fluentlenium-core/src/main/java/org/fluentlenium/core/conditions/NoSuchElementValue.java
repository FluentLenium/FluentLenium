package org.fluentlenium.core.conditions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Value to return when underlying element is not found.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface NoSuchElementValue {
    /**
     * Value to return when underlying element is not found.
     *
     * @return default value
     */
    boolean value();
}
