package io.fluentlenium.assertj.integration.element;

import io.fluentlenium.assertj.custom.FluentWebElementAssert;
import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static io.fluentlenium.assertj.AssertionTestSupport.assertThatAssertionErrorIsThrownBy;
import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

/**
 * Integration test for {@link FluentWebElementAssert}.
 */
public class FluentWebElementHasClassTest extends IntegrationTest {

    @Test
    public void testAssertOnOneOfManyClasses() {
        goTo(DEFAULT_URL);
        assertThat(el("#multiple-css-class")).hasClass("class1");
    }

    @Test
    public void testAssertOnSubstringOfAClass() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#multiple-css-class")).hasClass("wrongclass"))
                .isInstanceOf(AssertionError.class)
                .hasMessage(
                        "The element does not have the class: wrongclass. Actual class found"
                                + " : class1 class2 class3");
    }

    @Test
    public void shouldNotHaveClass() {
        goTo(DEFAULT_URL);
        assertThat(el("#oneline")).hasNotClass("clazz");
    }

    @Test
    public void shouldNotHaveClassWhenClassAttributeIsNotPresent() {
        goTo(DEFAULT_URL);
        assertThat(el("#multiple-css-class")).hasNotClass("clazz");
    }

    @Test
    public void shouldFailWhenHasClass() {
        goTo(DEFAULT_URL);
        assertThatAssertionErrorIsThrownBy(() -> assertThat(el("#multiple-css-class")).hasNotClass("class1"))
                .hasMessage("The element has class: class1");
    }

    @Test
    public void shouldHaveClasses() {
        goTo(DEFAULT_URL);
        assertThat(el("#multiple-css-class")).hasClasses("class2", "class3");
    }

    @Test
    public void shouldFailWhenNoClassAttributeIsPresent() {
        goTo(DEFAULT_URL);
        assertThatAssertionErrorIsThrownBy(() ->
                assertThat(el("#location")).hasClasses("class2", "class3")
        ).hasMessage("The element has no class attribute.");
    }

    @Test
    public void shouldFailWhenDoesntHaveAllClasses() {
        goTo(DEFAULT_URL);
        assertThatAssertionErrorIsThrownBy(() ->
                assertThat(el("#multiple-css-class")).hasClasses("class2", "class5")
        ).hasMessage("The element does not have all classes: [class2, class5]. "
                + "Actual classes found : class1 class2 class3");
    }

    @Test
    public void shouldNotHaveClasses() {
        goTo(DEFAULT_URL);
        assertThat(el("#multiple-css-class")).hasNotClasses("class2", "class5");
    }

    @Test
    public void shouldPassHasNotClassWhenNoClassAttributeIsPresent() {
        goTo(DEFAULT_URL);
        assertThat(el("#location")).hasNotClasses("class1", "class2");
    }

    @Test
    public void shouldFailWhenContainsClasses() {
        goTo(DEFAULT_URL);
        assertThatAssertionErrorIsThrownBy(() ->
                assertThat(el("#multiple-css-class")).hasNotClasses("class1", "class2")
        ).hasMessage("The element has the classes: [class1, class2]. Actual classes found : class1 class2 class3");
    }
}
