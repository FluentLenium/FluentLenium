package io.fluentlenium.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation configuration custom property.
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomProperty {
    /**
     * Property name
     *
     * @return property name
     */
    String name();

    /**
     * Property value
     *
     * @return property value
     */
    String value();
}
