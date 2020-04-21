package org.fluentlenium.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Using this annotation, the value of {@code toString()} method of the injected object will be extended with the list of hints
 * provided to the annotation.
 * <p>
 * Examples:
 * <pre>
 * public class Homepage {
 *
 *      &#064;FindBy(css = ".teaser img")
 *      &#064;LabelHint("teaser")
 *      private FluentWebElement teaserImage;
 *      //toString(): By.cssSelector: .teaser img (first) [teaser] (Lazy Element)
 *
 *      &#064;FindBy(css = ".teaser img")
 *      &#064;LabelHint({"img", "teaser"})
 *      private FluentWebElement teaserImage;
 *      //toString(): By.cssSelector: .teaser img (first) [img, teaser] (Lazy Element)
 *
 *      &#064;FindBy(css = ".teaser img")
 *      &#064;Label
 *      &#064;LabelHint({"img", "teaser"})
 *      private FluentWebElement teaserImage;
 *      //toString(): Homepage.teaserImage [img, teaser] (Lazy Element)
 *
 *      &#064;FindBy(css = ".teaser img")
 *      &#064;Label("teaserimg")
 *      &#064;LabelHint({"img", "teaser"})
 *      private FluentWebElement teaserImage;
 *      //toString(): teaserimg [img, teaser] (Lazy Element)
 * }
 * </pre>
 * A label hint can be added to a {@code @Page} annotated {@link org.fluentlenium.core.domain.FluentWebElement} or
 * {@link org.fluentlenium.core.domain.FluentList} field.
 * <p>
 * This annotation is independent from the {@link Label} annotation. Each one can be used without the other.
 * <p>
 * Defining a label hint can also be done inline on an a {@code FluentWebElement} or {@code FluentList} by calling the {@code withLabelHint()}
 * method on it.
 *
 * @see org.fluentlenium.core.inject.LabelAnnotations
 * @see org.fluentlenium.core.label.FluentLabelImpl
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
