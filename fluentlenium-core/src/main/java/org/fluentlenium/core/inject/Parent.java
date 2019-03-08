package org.fluentlenium.core.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Mark a field whose class declaration is annotated with one of Selenium's {@code Find...} annotation,
 * with this annotation to inject the parent container. Parent container in this case means parent in the HTML DOM.
 * <p>
 * It doesn't matter if the parent is a direct parent or is any element that is upper in the DOM relative to the current element.
 * What element the parent represents depends on the {@code Find...} annotation applied on the parent component or page class.
 * <p>
 * <h3>Examples</h3>
 * For an HTML snippet like
 * <pre>
 * &lt;div id="homepage">
 *      &lt;div class="component">
 *          &lt;div class="sub-component">
 *              ...
 *          &lt;/div>
 *      &lt;/div>
 * &lt;/div>
 * </pre>
 * there can be a custom component for the {@code div.component} element:
 * <pre>
 * &#064;FindBy(className = "component")
 * public class Component extends FluentWebElement {
 *      &#064;Parent
 *      public Homepage homepage;
 *      ...
 * }
 * </pre>
 * for which a sub-component can include it as a parent component:
 * <pre>
 * &#064;FindBy(className = "sub-component")
 * public class SubComponent extends FluentWebElement {
 *      &#064;Parent
 *      public Component parent;
 *      ...
 * }
 * </pre>
 * Similarly a parent can be created for {@code Component} in the form of a page:
 * <pre>
 * &#064;FindBy(id = "homepage")
 * public class Homepage extends FluentPage {
 *      ...
 * }
 * </pre>
 * This structure can be achieved with any page-component/component-page relationship using custom
 * {@link org.fluentlenium.core.FluentPage} and {@link org.fluentlenium.core.domain.FluentWebElement} implementations.
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Parent {
}
