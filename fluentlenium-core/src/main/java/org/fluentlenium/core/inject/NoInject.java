package org.fluentlenium.core.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * When this annotation is applied on a field FluentLenium injection is disabled for that.
 * <p>
 * It may be useful in scenarios when one wants to have a simple instance field setting it manually, and not relying on injection
 * either using a Selenium {@code Find...} annotation, or without using one relying on the field name based element lookup.
 * <p>
 * With that said if a field is marked as {@code NoInject}, none of the Selenium {@code Find...} annotations are necessary
 * on that field, because it won't have any effect.
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface NoInject {
}
