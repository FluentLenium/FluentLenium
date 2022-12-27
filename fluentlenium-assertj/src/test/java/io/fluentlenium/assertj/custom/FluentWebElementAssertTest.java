package io.fluentlenium.assertj.custom;

import io.fluentlenium.assertj.FluentLeniumAssertions;
import io.fluentlenium.core.domain.FluentWebElement;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.Dimension;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.AssertionTestSupport.assertThatAssertionErrorIsThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link FluentWebElementAssert}.
 */
public class FluentWebElementAssertTest {
    @Mock
    private FluentWebElement element;
    private FluentWebElementAssert elementAssert;

    @BeforeMethod
    public void before() {
        MockitoAnnotations.initMocks(this);
        elementAssert = FluentLeniumAssertions.assertThat(element);
    }

    @Test
    public void testIsEnabledOk() {
        when(element.present()).thenReturn(true);
        when(element.enabled()).thenReturn(true);
        elementAssert.isEnabled();
    }

    @Test
    public void testIsEnabledKo() {
        when(element.present()).thenReturn(true);
        when(element.enabled()).thenReturn(false);
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.isEnabled())
                .hasMessage("Element in assertion is present but not enabled");
    }

    @Test
    public void testIsNotEnabledOk() {
        when(element.present()).thenReturn(true);
        when(element.enabled()).thenReturn(false);
        elementAssert.isNotEnabled();
    }

    @Test
    public void testIsNotEnabledKo() {
        when(element.present()).thenReturn(true);
        when(element.enabled()).thenReturn(true);
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.isNotEnabled())
                .hasMessage("Element in assertion is present but enabled");
    }

    @Test
    public void testIsClickableOk() {
        when(element.present()).thenReturn(true);
        when(element.clickable()).thenReturn(true);
        elementAssert.isClickable();
    }

    @Test
    public void testIsClickableKo() {
        when(element.present()).thenReturn(true);
        when(element.clickable()).thenReturn(false);
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.isClickable())
                .hasMessage("Element in assertion is present but not clickable");
    }

    @Test
    public void testIsNotClickableOk() {
        when(element.present()).thenReturn(true);
        when(element.clickable()).thenReturn(false);
        elementAssert.isNotClickable();
    }

    @Test
    public void testIsNotClickableKo() {
        when(element.present()).thenReturn(true);
        when(element.clickable()).thenReturn(true);
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.isNotClickable())
                .hasMessage("Element in assertion is present but clickable");
    }

    @Test
    public void testIsPresentOk() {
        when(element.present()).thenReturn(true);
        elementAssert.isPresent();
    }

    @Test
    public void testIsPresentKo() {
        when(element.present()).thenReturn(false);
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.isPresent())
                .hasMessage("Element in assertion is not present");
    }

    @Test
    public void testIsNotPresentOk() {
        when(element.present()).thenReturn(false);
        elementAssert.isNotPresent();
    }

    @Test
    public void testIsNotPresentKo() {
        when(element.present()).thenReturn(true);
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.isNotPresent())
                .hasMessage("Element in assertion is present");
    }

    @Test
    public void testIsDisplayedOk() {
        when(element.present()).thenReturn(true);
        when(element.displayed()).thenReturn(true);
        elementAssert.isDisplayed();
    }

    @Test
    public void testIsDisplayedKo() {
        when(element.present()).thenReturn(true);
        when(element.displayed()).thenReturn(false);
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.isDisplayed())
                .hasMessage("Element in assertion is present but not displayed");
    }

    @Test
    public void testIsNotDisplayed() {
        when(element.present()).thenReturn(true);
        when(element.displayed()).thenReturn(false);
        elementAssert.isNotDisplayed();
    }

    @Test
    public void testIsNotDisplayedKo() {
        when(element.present()).thenReturn(true);
        when(element.displayed()).thenReturn(true);
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.isNotDisplayed())
                .hasMessage("Element in assertion is present but displayed");
    }

    @Test
    public void testIsNotSelectedOk() {
        when(element.present()).thenReturn(true);
        when(element.selected()).thenReturn(false);
        elementAssert.isNotSelected();
    }

    @Test
    public void testIsNotSelectedKo() {
        when(element.present()).thenReturn(true);
        when(element.selected()).thenReturn(true);
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.isNotSelected())
                .hasMessage("Element in assertion is present but selected");
    }

    @Test
    public void testIsSelectedOk() {
        when(element.present()).thenReturn(true);
        when(element.selected()).thenReturn(true);
        elementAssert.isSelected();
    }

    @Test
    public void testIsSelectedKo() {
        when(element.present()).thenReturn(true);
        when(element.selected()).thenReturn(false);
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.isSelected())
                .hasMessage("Element in assertion is present but not selected");
    }

    @Test
    public void testHasNameOk() {
        when(element.name()).thenReturn("some name");
        elementAssert.hasName("some name");
    }

    @Test
    public void testHasNameKo() {
        when(element.name()).thenReturn("other name");
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasName("some name"))
                .hasMessage("The element does not have the name: some name. Actual name found : other name");
    }

    @Test
    public void testHasValueOk() {
        when(element.value()).thenReturn("some value");
        elementAssert.hasValue("some value");
    }

    @Test
    public void testHasValueKo() {
        when(element.value()).thenReturn("other value");
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasValue("some value"))
                .hasMessage("The element does not have the value: some value. Actual value found : other value");
    }

    @Test
    public void testHasTagNameOk() {
        when(element.tagName()).thenReturn("some tag");
        elementAssert.hasTagName("some tag");
    }

    @Test
    public void testHasTagNameKo() {
        when(element.tagName()).thenReturn("other tag");
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasTagName("some tag"))
                .hasMessage("The element does not have tag: some tag. Actual tag found : other tag");
    }

    @Test
    public void testHasAttributeValueOk() {
        when(element.attribute("attribute")).thenReturn("some value");
        elementAssert.hasAttributeValue("attribute", "some value");
    }

    @Test
    public void testHasAttributeValueKo() {
        when(element.attribute("attribute")).thenReturn("other value");
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasAttributeValue("attribute", "some value"))
                .hasMessage("The attribute attribute does not have the value: some value. Actual value : other value");
    }

    @Test
    public void testHasAttributeValueKoWhenAttributeIsMissing() {
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasAttributeValue("attribute", "some value"))
                .hasMessage("The element does not have attribute attribute");
    }

    @Test
    public void testHasAttributeOk() {
        when(element.attribute("attribute")).thenReturn("some value");
        elementAssert.hasAttribute("attribute");
    }

    @Test
    public void testHasAttributeKo() {
        when(element.attribute("attribute")).thenReturn(null);
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasAttribute("attribute"));
    }

    @Test
    public void testHasNotAttributeOk() {
        when(element.attribute("attribute")).thenReturn(null);
        elementAssert.hasNotAttribute("attribute");
    }

    @Test
    public void testHasNotAttributeKo() {
        when(element.attribute("attribute")).thenReturn("some value");
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasNotAttribute("attribute"));
    }

    @Test
    public void testHasIdOk() {
        when(element.id()).thenReturn("some id");
        elementAssert.hasId("some id");
    }

    @Test
    public void testHasIdKo() {
        when(element.id()).thenReturn("other id");
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasId("some id"))
                .hasMessage("The element does not have the id: some id. Actual id found : other id");
    }

    @Test
    public void testHasDimensionOk() {
        when(element.size()).thenReturn(new Dimension(1, 2));
        elementAssert.hasDimension(new Dimension(1, 2));
    }

    @Test
    public void testHasDimensionKo() {
        when(element.size()).thenReturn(new Dimension(2, 1));
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasDimension(new Dimension(1, 2)))
                .hasMessage("The element does not have the same size: (1, 2). Actual size found : (2, 1)");
    }

    @Test
    public void testHasClassOk() {
        when(element.attribute("class")).thenReturn("some-class");
        elementAssert.hasClass("some-class");
    }

    @Test
    public void testHasClassKo() {
        when(element.attribute("class")).thenReturn("other-class");
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasClass("some-class"))
                .hasMessage("The element does not have the class: some-class. Actual class found : other-class");
    }

    @Test
    public void shouldNotHaveClass() {
        when(element.attribute("class")).thenReturn("clazz other-clazz");
        elementAssert.hasNotClass("not-class");
    }

    @Test
    public void shouldNotHaveClassWhenClassAttributeIsNotPresent() {
        elementAssert.hasNotClass("clazz");
    }

    @Test
    public void shouldFailWhenHasClass() {
        when(element.attribute("class")).thenReturn("clazz other-clazz");
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasNotClass("clazz"))
                .hasMessage("The element has class: clazz");
    }

    @Test
    public void shouldHaveClasses() {
        when(element.attribute("class")).thenReturn("clazz clazz2 clazz3 clazz4");
        elementAssert.hasClasses("clazz", "clazz2", "clazz4");
    }

    @Test
    public void shouldFailWhenNoClassAttributeIsPresent() {
        when(element.attribute("class")).thenReturn(null);
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasClasses("clazz", "clazz2", "clazz4"))
                .hasMessage("The element has no class attribute.");
    }

    @Test
    public void shouldFailWhenDoesntHaveAllClasses() {
        when(element.attribute("class")).thenReturn("clazz clazz2 clazz3");
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasClasses("clazz", "clazz5"))
                .hasMessage("The element does not have all classes: [clazz, clazz5]. "
                        + "Actual classes found : clazz clazz2 clazz3");
    }

    @Test
    public void shouldNotHaveClasses() {
        when(element.attribute("class")).thenReturn("clazz clazz2 clazz3");
        elementAssert.hasNotClasses("clazz2", "clazz4");
    }

    @Test
    public void shouldPassHasNotClassWhenNoClassAttributeIsPresent() {
        elementAssert.hasNotClasses("clazz2", "clazz4");
    }

    @Test
    public void shouldFailWhenContainsClasses() {
        when(element.attribute("class")).thenReturn("clazz clazz2 clazz3");
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasNotClasses("clazz2", "clazz3"))
                .hasMessage("The element has the classes: [clazz2, clazz3]. "
                        + "Actual classes found : clazz clazz2 clazz3");
    }

    @Test
    public void testHasClassSubstringKo() {
        when(element.attribute("class")).thenReturn("yolokitten");
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasClass("yolo"))
                .hasMessage("The element does not have the class: yolo. Actual class found : yolokitten");
    }

    @Test
    public void testHasTextOk() {
        when(element.text()).thenReturn("There is a 5% increase");
        elementAssert.hasText("There is a 5% increase");
    }

    @Test
    public void testHasTextContainingOk() {
        when(element.text()).thenReturn("There is a 5% increase");
        elementAssert.hasTextContaining("There is a 5%");
    }

    @Test
    public void shouldHaveTextMatching() {
        when(element.text()).thenReturn("There is a 5% increase");
        elementAssert.hasTextMatching(".*\\d%.*");
    }

    @Test
    @Ignore("https://github.com/FluentLenium/FluentLenium/issues/857")
    public void shouldFailWhenHasTextNotMatching() {
        when(element.text()).thenReturn("There is a 5% increase");
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasTextMatching("There s a"))
                .hasMessage("");
    }

    @Test
    public void testHasNotTextPositive() {
        when(element.text()).thenReturn("Something");
        elementAssert.hasNotText("Text which isn't above");
    }

    @Test
    public void testHasNotTextNegative() {
        when(element.text()).thenReturn("Something written here");
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasNotText("Something"))
                .hasMessage("The element contains the text: Something");
    }

    @Test
    public void testHasNotTextContainingPositive() {
        when(element.text()).thenReturn("Something");
        elementAssert.hasNotTextContaining("Text which isn't above");
    }

    @Test
    public void testHasNotTextContainingNegative() {
        when(element.text()).thenReturn("Something written here");
        assertThatAssertionErrorIsThrownBy(() -> elementAssert.hasNotTextContaining("Something"))
            .hasMessage("The element contains the text: Something");
    }

    @Test
    public void testHasTextContainingWithSpecialCharactersInElement() {
        String textWithStringFormatError = "%A";
        when(element.text()).thenReturn(textWithStringFormatError);
        elementAssert.hasTextContaining(textWithStringFormatError);
    }

    @Test
    public void testHasTextWithSpecialCharactersInElement() {
        String textWithStringFormatError = "%A";
        when(element.text()).thenReturn(textWithStringFormatError);
        elementAssert.hasText(textWithStringFormatError);
    }

    @Test
    public void testHasNoRaceConditionInHasText() {
        String textToFind = "someText";
        String firstActualText = "someOtherText";

        when(element.text()).thenReturn(firstActualText, textToFind);

        try {
            elementAssert.hasText(textToFind);
            fail("Expected assertion error");
        } catch (AssertionError assertionError) {
            assertThat(assertionError.getMessage()).contains("Actual text found : " + firstActualText);
        }
    }

    @Test
    public void testHasNoRaceConditionInHasTextContaining() {
        String textToFind = "someText";
        String firstActualText = "someOtherText";

        when(element.text()).thenReturn(firstActualText, textToFind);

        try {
            elementAssert.hasTextContaining(textToFind);
            fail("Expected assertion error");
        } catch (AssertionError assertionError) {
            assertThat(assertionError.getMessage()).contains("Actual text found : " + firstActualText);
        }
    }

    @Test
    public void testHasMultipleClassesOk() {
        when(element.attribute("class")).thenReturn("yolokitten mark");
        elementAssert.hasClass("mark");
    }
}
