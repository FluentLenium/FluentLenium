package org.fluentlenium.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark a field as a Page Object that should be automatically created by FluentLenium.
 *
 * <pre>
 * &#064;PageUrl("/")
 * public class Homepage extends FluentPage {
 *
 *      //@FindBy annotated elements
 *      //...
 * }
 *
 * public class HomepageTest extends FluentPage {
 *
 *      &#064;Page
 *      Homepage homepage;
 *
 *      //Homepage is instantiated and can be used by test methods.
 * }
 * </pre>
 * <p>
 * If you are using Cucumber, it is not just page object classes but also step definitions classes
 * that can be instantiated and injected using this annotation.
 * <p>
 * Although injecting step definitions classes into other step definitions classes might not be the best approach,
 * it is still achievable using this annotation if needed.
 * <pre>
 * public class LoginSteps extends FluentCucumberTest {
 *
 *     &#064;Given("a(n) {} user logged in")
 *     public void aUserLoggedIn(User user) {
 *         //login logic
 *     }
 * }
 *
 * public class HomepageSteps extends FluentCucumberTest {
 *
 *      &#064;Page
 *      Homepage homepage;
 *      &#064;Page
 *      LoginSteps loginSteps;
 *
 *      &#064;Given("{} user does stuff")
 *      public void userDoesStuff(User user) {
 *          loginSteps.aUserLoggedIn(user);
 *          //does some stuff
 *      }
 * }
 * </pre>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Page {
}
