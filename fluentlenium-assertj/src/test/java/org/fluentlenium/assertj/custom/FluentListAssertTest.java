package org.fluentlenium.assertj.custom;

import com.google.common.collect.Lists;
import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.Dimension;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.AssertionTestSupport.assertThatAssertionErrorIsThrownBy;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link FluentListAssert}.
 */
public class FluentListAssertTest {

    @Mock
    private FluentList<FluentWebElement> fluentList;
    private FluentListAssert listAssert;

    @BeforeMethod
    public void before() {
        MockitoAnnotations.initMocks(this);
        listAssert = FluentLeniumAssertions.assertThat(fluentList);
    }

    @Test
    public void testHasTextOk() {
        when(fluentList.texts()).thenReturn(singletonList("some text"));
        listAssert.hasText("some text");
    }

    @Test
    public void testHasTextKo() {
        when(fluentList.texts()).thenReturn(Lists.newArrayList("some text", "other text"));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasText("absent text"))
                .hasMessage("No selected elements contains text: absent text. "
                        + "Actual texts found: [some text, other text]");
    }

    @Test
    public void hasTextMatchingOk() {
        when(fluentList.texts()).thenReturn(Lists.newArrayList("Pharmacy", "Hospital"));
        listAssert.hasTextMatching("Pha\\w+cy");
    }

    @Test
    public void hasTextMatchingKo() {
        when(fluentList.texts()).thenReturn(Lists.newArrayList("Pharmacy", "Hospital"));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasTextMatching("Pha\\w+cy\\8"))
                .hasMessage("No selected elements contains text matching: Pha\\w+cy\\8. "
                        + "Actual texts found: [Pharmacy, Hospital]");
    }

    @Test
    public void testHasNotTextOk() {
        when(fluentList.texts()).thenReturn(singletonList("other text"));
        listAssert.hasNotText("some text");
    }

    @Test
    public void testHasNotTextKo() {
        when(fluentList.texts()).thenReturn(Lists.newArrayList("some text", "other text"));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasNotText("other text"))
                .hasMessage("At least one selected elements contains text: other text. "
                        + "Actual texts found: [some text, other text]");
    }

    @Test
    public void testHasSizeOk() {
        when(fluentList.count()).thenReturn(7);
        listAssert.isNotEmpty();
        listAssert.hasSize(7);
    }

    @Test
    public void testHasSizeZeroOk() {
        when(fluentList.count()).thenReturn(0);
        listAssert.hasSize(0);
        listAssert.isEmpty();
    }

    @Test
    public void testHasSizeZeroKo() {
        when(fluentList.count()).thenReturn(0);
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasSize(1)).hasMessage("Expected size: 1. Actual size: 0.");
    }

    @Test
    public void testHasSizeEmptyKo() {
        when(fluentList.count()).thenReturn(1);
        assertThatAssertionErrorIsThrownBy(() -> listAssert.isEmpty()).hasMessage("Expected size: 0. Actual size: 1.");
    }

    @Test
    public void testHasSizeNotEmptyKo() {
        when(fluentList.count()).thenReturn(0);
        assertThatAssertionErrorIsThrownBy(() -> listAssert.isNotEmpty()).hasMessage("Actual size: 0 is equal to: 0");
    }

    @Test
    public void testHasSizeZeroInBuilder() {
        when(fluentList.count()).thenReturn(0);
        listAssert.hasSize().equalTo(0);
    }

    @Test
    public void testHasSizeNotEqualOk() {
        when(fluentList.count()).thenReturn(0);
        listAssert.hasSize().notEqualTo(1);
    }

    @Test
    public void testHasSizeNotEqualKo() {
        when(fluentList.count()).thenReturn(0);
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasSize().notEqualTo(0))
                .hasMessage("Actual size: 0 is equal to: 0");
    }

    @Test
    public void testHasSizeKo() {
        when(fluentList.count()).thenReturn(7);
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasSize(5)).hasMessage("Expected size: 5. Actual size: 7.");
    }

    @Test
    public void testHasSizeLessThanOk() {
        when(fluentList.count()).thenReturn(7);
        listAssert.hasSize().lessThan(9);
    }

    @Test
    public void testHasSizeLessThanKo() {
        when(fluentList.count()).thenReturn(7);
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasSize().lessThan(7))
                .hasMessage("Actual size: 7 is not less than: 7");
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasSize().lessThan(6))
                .hasMessage("Actual size: 7 is not less than: 6");
    }

    @Test
    public void testHasSizeLessThanOrEqualToOk() {
        when(fluentList.count()).thenReturn(7);
        listAssert.hasSize().lessThanOrEqualTo(7);
        listAssert.hasSize().lessThanOrEqualTo(8);
    }

    @Test
    public void testHasSizeLessThanOrEqualToKo() {
        when(fluentList.count()).thenReturn(7);
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasSize().lessThanOrEqualTo(6))
                .hasMessage("Actual size: 7 is not less than or equal to: 6");
    }

    @Test
    public void testHasSizeGreaterThanOk() {
        when(fluentList.count()).thenReturn(7);
        listAssert.hasSize().greaterThan(6);
    }

    @Test
    public void testHasSizeGreaterThanKo() {
        when(fluentList.count()).thenReturn(7);
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasSize().greaterThan(7))
                .hasMessage("Actual size: 7 is not greater than: 7");
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasSize().greaterThan(8))
                .hasMessage("Actual size: 7 is not greater than: 8");
    }

    @Test
    public void testHasSizeGreaterThanOrEqualToOk() {
        when(fluentList.count()).thenReturn(7);
        listAssert.hasSize().greaterThanOrEqualTo(7);
        listAssert.hasSize().greaterThanOrEqualTo(6);
    }

    @Test
    public void testHasSizeGreaterThanOrEqualToKo() {
        when(fluentList.count()).thenReturn(7);
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasSize().greaterThanOrEqualTo(8))
                .hasMessage("Actual size: 7 is not greater than or equal to: 8");
    }

    @Test
    public void testHasIdOk() {
        when(fluentList.ids()).thenReturn(singletonList("some-id"));
        listAssert.hasId("some-id");
    }

    @Test
    public void testHasIdKo() {
        when(fluentList.ids()).thenReturn(singletonList("other-id"));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasId("some-id"))
                .hasMessage("No selected elements have id: some-id. Actual ids found : [other-id]");
    }

    @Test
    public void testHasValueOk() {
        when(fluentList.values()).thenReturn(Lists.newArrayList("1", "2", "3"));
        listAssert.hasValue("1");
    }

    @Test
    public void testHasValueKo() {
        when(fluentList.values()).thenReturn(Lists.newArrayList("1", "2", "3"));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasValue("4"))
                .hasMessage("No selected elements have value: 4. Actual values found : [1, 2, 3]");
    }

    @Test
    public void testHasNameOk() {
        when(fluentList.names()).thenReturn(Lists.newArrayList("name-one", "name-two"));
        listAssert.hasName("name-one");
    }

    @Test
    public void testHasNameKo() {
        when(fluentList.names()).thenReturn(Lists.newArrayList("name-one", "name-two"));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasName("name-three"))
                .hasMessage("No selected elements have name: name-three. Actual names found : [name-one, name-two]");
    }

    @Test
    public void testHasTagNameOk() {
        when(fluentList.tagNames()).thenReturn(Lists.newArrayList("span", "div"));
        listAssert.hasTagName("span");
    }

    @Test
    public void testHasTagNamedKo() {
        when(fluentList.tagNames()).thenReturn(Lists.newArrayList("span", "div"));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasTagName("p"))
                .hasMessage("No selected elements have tag: p. Actual tags found : [span, div]");
    }

    @Test
    public void testHasIdEmptyKo() {
        when(fluentList.ids()).thenReturn(emptyList());
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasId("some-id"))
                .hasMessage("List is empty. Please make sure you use correct selector.");
    }

    @Test
    public void testHasClassOk() {
        when(fluentList.attributes("class")).thenReturn(Lists.newArrayList("some-class", "unknown-class"));
        listAssert.hasClass("some-class");
    }

    @Test
    public void testHasClassKo() {
        when(fluentList.attributes("class")).thenReturn(Lists.newArrayList("other-class", "unknown-class"));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasClass("some-class"))
                .hasMessage("No selected elements have class: some-class. "
                        + "Actual classes found : other-class, unknown-class");
    }

    @Test
    public void testHasClassEmptyKo() {
        when(fluentList.attributes("class")).thenReturn(emptyList());
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasClass("some-class"))
                .hasMessage("List is empty. Please make sure you use correct selector.");
    }

    @Test
    public void shouldNotHaveClass() {
        when(fluentList.attributes("class")).thenReturn(Lists.newArrayList("some-class", "unknown-class"));
        listAssert.hasNotClass("clazz");
    }

    @Test
    public void shouldNotHaveClassWhenNoElementHasClass() {
        when(fluentList.attributes("class")).thenReturn(Lists.newArrayList(null, null));
        listAssert.hasNotClass("clazz");
    }

    @Test
    public void shouldFailWhenAtLeastOneElementHasClass() {
        when(fluentList.attributes("class")).thenReturn(Lists.newArrayList("some-class", "unknown-class"));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasNotClass("some-class"))
                .hasMessage("At least one selected element has class: [some-class]");
    }

    @Test
    public void shouldHaveClasses() {
        when(fluentList.attributes("class")).thenReturn(Lists.newArrayList("class1 class2", "class1 class2 class3", "class4"));
        listAssert.hasClasses("class1", "class2");
    }

    @Test
    public void shouldFailHasClassesWhenNoElementHasClass() {
        when(fluentList.attributes("class")).thenReturn(Lists.newArrayList("class1 class2", "class1 class2 class3", "class4"));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasClasses("class2", "class4"))
                .hasMessage("No selected element have classes: class2, class4. "
                        + "Actual classes found : class1 class2, class1 class2 class3, class4");
    }

    @Test
    public void shouldNotHaveClasses() {
        when(fluentList.attributes("class")).thenReturn(Lists.newArrayList("class1 class2", "class1 class2 class3", "class4"));
        listAssert.hasNotClasses("class2", "class4");
    }

    @Test
    public void shouldNotHaveClassesWhenNoElementHasClass() {
        when(fluentList.attributes("class")).thenReturn(Lists.newArrayList(null, null));
        listAssert.hasNotClasses("class2", "class4");
    }

    @Test
    public void shouldFailWhenHasClasses() {
        when(fluentList.attributes("class")).thenReturn(Lists.newArrayList("class1 class2", "class1 class2 class3", "class4"));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasNotClasses("class2", "class3"))
                .hasMessage("At least one selected element has classes: [class2, class3]");
    }

    @Test
    public void testSubstringKo() {
        when(fluentList.attributes("class")).thenReturn(singletonList("yolokitten"));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasClass("yolo"))
                .hasMessage("No selected elements have class: yolo. Actual classes found : yolokitten");
    }

    @Test
    public void testHasMultipleClassesOk() {
        when(fluentList.attributes("class")).thenReturn(singletonList("yolokitten mark"));
        listAssert.hasClass("mark");
    }

    @Test
    public void testHasMultipleClassesOkBanana() {
        when(fluentList.attributes("class")).thenReturn(Arrays.asList("beta product", "alpha male"));
        listAssert.hasClass("male");
    }

    @Test
    public void testHasDimensionOk() {
        Dimension dimensionOne = new Dimension(1, 2);
        Dimension dimensionTwo = new Dimension(3, 4);
        when(fluentList.dimensions()).thenReturn(Lists.newArrayList(dimensionOne, dimensionTwo));
        listAssert.hasDimension(new Dimension(1, 2));
    }

    @Test
    public void testHasDimensionKo() {
        Dimension dimensionOne = new Dimension(1, 2);
        Dimension dimensionTwo = new Dimension(3, 4);
        when(fluentList.dimensions()).thenReturn(Lists.newArrayList(dimensionOne, dimensionTwo));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasDimension(new Dimension(5, 6)))
                .hasMessage("No selected elements have dimension: (5, 6). Actual dimensions found : [(1, 2), (3, 4)]");
    }

    @Test
    public void testHasAttributeValueOk() {
        when(fluentList.attributes("name")).thenReturn(Lists.newArrayList("name-one", "name-two"));
        listAssert.hasAttributeValue("name", "name-one");
    }

    @Test
    public void testHasAttributeValueKo() {
        when(fluentList.attributes("name")).thenReturn(Lists.newArrayList("name-one", "name-two"));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasAttributeValue("name", "name-three"))
                .hasMessage("No selected elements have attribute name with value: name-three. "
                        + "Actual values found: [name-one, name-two]");
    }

    @Test
    public void shouldHaveAttribute() {
        when(fluentList.attributes("name")).thenReturn(Lists.newArrayList("name-one", "name-two"));
        listAssert.hasAttribute("name");
    }

    @Test
    public void shouldFailWhenNoElementHasAttribute() {
        when(fluentList.attributes("name")).thenReturn(Lists.newArrayList(null, null));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasAttribute("name"))
                .hasMessage("No selected element has attribute name");
    }

    @Test
    public void shouldNotHaveAttribute() {
        when(fluentList.attributes("name")).thenReturn(Lists.newArrayList(null, null));
        listAssert.hasNotAttribute("name");
    }

    @Test
    public void shouldFailWhenHasAttribute() {
        when(fluentList.attributes("name")).thenReturn(Lists.newArrayList("name-one", "name-two"));
        assertThatAssertionErrorIsThrownBy(() -> listAssert.hasNotAttribute("name"))
                .hasMessage("At least one selected element has attribute name");
    }

    @Test
    public void emptyListErrorMessage() {
        when(fluentList.texts()).thenReturn(emptyList());

        assertThatThrownBy(() -> listAssert.hasText("John"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("List is empty. Please make sure you use correct selector.");
    }
}
