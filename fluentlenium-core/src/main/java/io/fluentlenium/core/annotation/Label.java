package io.fluentlenium.core.annotation;

import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.inject.LabelAnnotations;
import io.fluentlenium.core.label.FluentLabel;
import io.fluentlenium.core.label.FluentLabelImpl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define a label on an injected {@link FluentWebElement} or
 * {@link FluentList}.
 * <p>
 * By default the injected element's original {@code toString()} value is returned but with
 * having the {@code @Label} annotation with different configurations that toString value can
 * be overridden. Using it can provide more meaningful error messages during test execution and debugging.
 * <p>
 * {@code FluentWebElement} Examples:
 * <pre>
 * public class HomepageHeader {
 *
 *      &#064;FindBy(css = "#header")
 *      private FluentWebElement header;
 *      //toString(): By.cssSelector: #header (first) (Lazy Element)
 *
 *      &#064;FindBy(css = "#header")
 *      &#064;Label
 *      private FluentWebElement headerDefaultLabel;
 *      //toString(): HomepageHeader.headerDefaultLabel (Lazy Element)
 *
 *      &#064;FindBy(css = "#header")
 *      &#064;Label("customLabel")
 *      private FluentWebElement headerCustomLabel;
 *      //toString(): customLabel (Lazy Element)
 * }
 * </pre>
 * <p>
 * {@code FluentList} Examples:
 * <pre>
 * public class HomepageHeader {
 *      &#064;FindBy(css = ".footer-link")
 *      private FluentList&lt;FluentWebElement footerLinks;
 *      //toString(): By.cssSelector: .footer-link (&lt;toString() of the underlying list of FluentWebElements)
 *
 *      &#064;FindBy(css = ".footer-link")
 *      &#064;Label
 *      private FluentList&lt;FluentWebElement footerLinksDefaultLabel;
 *      //toString(): HomepageHeader.footerLinksDefaultLabel (&lt;toString() of the underlying list of FluentWebElements)
 *
 *      &#064;FindBy(css = ".footer-link")
 *      &#064;Label("customLabel")
 *      private FluentList&lt;FluentWebElement footerLinksCustomLabel;
 *      //toString(): customLabel (&lt;toString() of the underlying list of FluentWebElements)
 * }
 * </pre>
 * <p>
 * This annotation is independent from the {@link LabelHint} annotation. Each one can be used without the other.
 * <p>
 * Defining a label can also be done inline on an a {@code FluentWebElement} or {@code FluentList}
 * by calling the {@code withLabel()} method on it.
 *
 * @see LabelAnnotations
 * @see FluentLabelImpl
 * @see FluentLabel#withLabel(String)
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
