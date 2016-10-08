package org.fluentlenium.core.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Mark a field with this annotation to inject the parent container.
 */
@Target({METHOD, CONSTRUCTOR, FIELD})
@Retention(RUNTIME)
public @interface Parent {
}
