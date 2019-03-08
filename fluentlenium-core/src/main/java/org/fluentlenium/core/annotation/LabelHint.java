package org.fluentlenium.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Using this annotation the value of {@code toString()} method of the injected object will be extended with the list of hints
 * provided to the annotation. For example in case of:
 * <pre>
 * &#064;FindBy(css = ".teaser img")
 * &#064;LabelHint({"img", "teaser"})
 * private FluentWebElement teaserImage;
 * </pre>
 * <p>
 * {@code [img, teaser]} will be attached to the toString.
 * <p>
 * A label hint can be added on an injected {@link org.fluentlenium.core.domain.FluentWebElement} or
 * {@link org.fluentlenium.core.domain.FluentList}.
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
