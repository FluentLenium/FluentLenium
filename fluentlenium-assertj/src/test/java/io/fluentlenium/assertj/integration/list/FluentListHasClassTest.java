package io.fluentlenium.assertj.integration.list;

import io.fluentlenium.assertj.AssertionTestSupport;
import io.fluentlenium.assertj.custom.FluentListAssert;
import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration test for {@link FluentListAssert}.
 */
public class FluentListHasClassTest extends IntegrationTest {

    @Test
    public void testHasClassPositive() {
        goTo(DEFAULT_URL);
        assertThat($("button[id*=multiple-css-class]")).hasClass("class1");
    }

    @Test
    public void testHasClassNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat($("#multiple-css-class")).hasClass("cla"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements have class: cla");
    }

    @Test
    public void shouldNotHaveClass() {
        goTo(DEFAULT_URL);
        assertThat($("span[id*=id]")).hasNotClass("big");
    }

    @Test
    public void shouldNotHaveClassWhenNoElementHasClass() {
        goTo(DEFAULT_URL);
        assertThat($("input")).hasNotClass("clazz");
    }

    @Test
    public void shouldFailWhenAtLeastOneElementHasClass() {
        goTo(DEFAULT_URL);
        AssertionTestSupport.assertThatAssertionErrorIsThrownBy(() -> assertThat($("span")).hasNotClass("small"))
                .hasMessage("At least one selected element has class: [small]");
    }

    @Test
    public void shouldHaveClasses() {
        goTo(DEFAULT_URL);
        assertThat($("button[id*=multiple-css-class]")).hasClasses("class2", "class3");
    }

    @Test
    public void shouldFailHasClassesWhenNoElementHasClass() {
        goTo(DEFAULT_URL);
        AssertionTestSupport.assertThatAssertionErrorIsThrownBy(() ->
                assertThat($("button[id*=multiple-css-class]")).hasClasses("class2", "class4")
        ).hasMessage("No selected element have classes: class2, class4. "
                + "Actual classes found : class1 class2 class3, class1 class2 class3");
    }

    @Test
    public void shouldNotHaveClasses() {
        goTo(DEFAULT_URL);
        assertThat($("button[id*=multiple-css-class]")).hasNotClasses("class2", "class4");
    }

    @Test
    public void shouldNotHaveClassesWhenNoElementHasClass() {
        goTo(DEFAULT_URL);
        assertThat($("input")).hasNotClasses("class2", "class4");
    }

    @Test
    public void shouldFailWhenHasClasses() {
        goTo(DEFAULT_URL);
        AssertionTestSupport.assertThatAssertionErrorIsThrownBy(() ->
                assertThat($("button[id*=multiple-css-class]")).hasNotClasses("class2", "class3")
        ).hasMessage("At least one selected element has classes: [class2, class3]");
    }
}
