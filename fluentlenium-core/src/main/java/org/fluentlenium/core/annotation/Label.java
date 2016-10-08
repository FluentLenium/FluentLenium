package org.fluentlenium.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define a label on a injected {@link org.fluentlenium.core.domain.FluentWebElement} or
 * {@link org.fluentlenium.core.domain.FluentList}
 *
 * @see org.fluentlenium.core.label.FluentLabel#withLabel(String)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Label {
    /**
     * Label value.
     * <p>
     * If not defined, it will use the class name and field name as the label.
     *
     * @return label value
     */
    String value() default "";
}
