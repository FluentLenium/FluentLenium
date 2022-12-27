package io.fluentlenium.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Experimental: Find element in shadow root using CSS selector
 *
 * <pre>
 * public class HomepageTest extends FluentPage {
 *
 *      &#064;Unshadow(css = {"outer-shadow-root", "inner-shadow-root", ".element"})
 *      FluentWebElement element;
 *
 *      //Element is instantiated and can be used by test methods.
 * }
 * </pre>
 *
 * It can also handle List and Set collections if many objects are found
 * <pre>
 * public class HomepageTest extends FluentPage {
 *
 *      &#064;Unshadow(css = {"outer-shadow-root", "inner-shadow-root", "div"})
 *      List&lt;FluentWebElement&gt; elementsList;
 *
 *      &#064;Unshadow(css = {"outer-shadow-root", "inner-shadow-root", "span"})
 *      Set&lt;FluentWebElement&gt; elementsSet;
 *
 *      //elementsList and elementsSet are instantiated and can be used by test methods.
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Unshadow {
  String[] css();
}
