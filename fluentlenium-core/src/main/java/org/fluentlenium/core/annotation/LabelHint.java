package org.fluentlenium.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add a label hint on a injected {@link org.fluentlenium.core.domain.FluentWebElement} or
 * {@link org.fluentlenium.core.domain.FluentList}
 *
 * @see org.fluentlenium.core.label.FluentLabel#withLabelHint(String...)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LabelHint {
    /**
     * Array of label hints
     *
     * @return array of label hints
     */
    String[] value();
}
