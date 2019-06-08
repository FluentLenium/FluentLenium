package org.fluentlenium.assertj.custom;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.Dimension;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link FluentWebElementAssert}.
 */
public class FluentWebElementTest {
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

    @Test(expectedExceptions = AssertionError.class)
    public void testIsEnabledKo() {
        when(element.enabled()).thenReturn(false);
        elementAssert.isEnabled();
    }

    @Test
    public void testIsNotEnabledOk() {
        when(element.present()).thenReturn(true);
        when(element.enabled()).thenReturn(false);
        elementAssert.isNotEnabled();
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testIsNotEnabledKo() {
        when(element.enabled()).thenReturn(true);
        elementAssert.isNotEnabled();
    }

    @Test
    public void testIsClickableOk() {
        when(element.present()).thenReturn(true);
        when(element.clickable()).thenReturn(true);
        elementAssert.isClickable();
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testIsClickableKo() {
        when(element.clickable()).thenReturn(false);
        elementAssert.isClickable();
    }

    @Test
    public void testIsNotClickableOk() {
        when(element.present()).thenReturn(true);
        when(element.clickable()).thenReturn(false);
        elementAssert.isNotClickable();
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testIsNotClickableKo() {
        when(element.clickable()).thenReturn(true);
        elementAssert.isNotClickable();
    }

    @Test
    public void testIsPresentOk() {
        when(element.present()).thenReturn(true);
        elementAssert.isPresent();
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testIsPresentKo() {
        when(element.present()).thenReturn(false);
        elementAssert.isPresent();
    }

    @Test
    public void testIsNotPresentOk() {
        when(element.present()).thenReturn(false);
        elementAssert.isNotPresent();
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testIsNotPresentKo() {
        when(element.present()).thenReturn(true);
        elementAssert.isNotPresent();
    }

    @Test
    public void testIsDisplayedOk() {
        when(element.present()).thenReturn(true);
        when(element.displayed()).thenReturn(true);
        elementAssert.isDisplayed();
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testIsDisplayedKo() {
        when(element.displayed()).thenReturn(false);
        elementAssert.isDisplayed();
    }

    @Test
    public void testIsNotDisplayed() {
        when(element.present()).thenReturn(true);
        when(element.displayed()).thenReturn(false);
        elementAssert.isNotDisplayed();
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testIsSelected() {
        when(element.displayed()).thenReturn(true);
        elementAssert.isNotDisplayed();
    }

    @Test
    public void testIsNotSelectedOk() {
        when(element.present()).thenReturn(true);
        when(element.selected()).thenReturn(false);
        elementAssert.isNotSelected();
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testIsNotSelectedKo() {
        when(element.selected()).thenReturn(true);
        elementAssert.isNotSelected();
    }

    @Test
    public void testIsSelectedOk() {
        when(element.present()).thenReturn(true);
        when(element.selected()).thenReturn(true);
        elementAssert.isSelected();
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testIsSelectedKo() {
        when(element.selected()).thenReturn(false);
        elementAssert.isSelected();
    }

    @Test
    public void testHasNameOk() {
        when(element.name()).thenReturn("some name");
        elementAssert.hasName("some name");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testHasNameKo() {
        when(element.name()).thenReturn("other name");
        elementAssert.hasName("some name");
    }

    @Test
    public void testHasValueOk() {
        when(element.value()).thenReturn("some value");
        elementAssert.hasValue("some value");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testHasValueKo() {
        when(element.value()).thenReturn("other value");
        elementAssert.hasValue("some value");
    }

    @Test
    public void testHasTagNameOk() {
        when(element.tagName()).thenReturn("some tag");
        elementAssert.hasTagName("some tag");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testHasTagNameKo() {
        when(element.tagName()).thenReturn("other tag");
        elementAssert.hasTagName("some tag");
    }

    @Test
    public void testHasAttributeValueOk() {
        when(element.attribute("attribute")).thenReturn("some value");
        elementAssert.hasAttributeValue("attribute", "some value");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testHasAttributeValueKo() {
        when(element.attribute("attribute")).thenReturn("other value");
        elementAssert.hasAttributeValue("attribute", "some value");
    }

    @Test
    public void testHasAttributeOk() {
        when(element.attribute("attribute")).thenReturn("some value");
        elementAssert.hasAttribute("attribute");
    }

    @Test
    public void testHasAttributeKo() {
        when(element.attribute("attribute")).thenReturn(null);
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> elementAssert.hasAttribute("attribute"));
    }

    @Test
    public void testHasNotAttributeOk() {
        when(element.attribute("attribute")).thenReturn(null);
        elementAssert.hasNotAttribute("attribute");
    }

    @Test
    public void testHasNotAttributeKo() {
        when(element.attribute("attribute")).thenReturn("some value");
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> elementAssert.hasNotAttribute("attribute"));
    }

    @Test
    public void testHasIdOk() {
        when(element.id()).thenReturn("some id");
        elementAssert.hasId("some id");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testHasIdKo() {
        when(element.id()).thenReturn("other id");
        elementAssert.hasId("some id");
    }

    @Test
    public void testHasDimensionOk() {
        when(element.size()).thenReturn(new Dimension(1, 2));
        elementAssert.hasDimension(new Dimension(1, 2));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testHasDimensionKo() {
        when(element.size()).thenReturn(new Dimension(2, 1));
        elementAssert.hasDimension(new Dimension(1, 2));
    }

    @Test
    public void testHasClassOk() {
        when(element.attribute("class")).thenReturn("some-class");
        elementAssert.hasClass("some-class");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testHasClassKo() {
        when(element.attribute("class")).thenReturn("other-class");
        elementAssert.hasClass("some-class");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testSubstringKo() {
        when(element.attribute("class")).thenReturn("yolokitten");
        elementAssert.hasClass("yolo");
    }

    @Test
    public void testHasTextOk() {
        when(element.text()).thenReturn("There is a 5% increase");
        elementAssert.hasText("There is a 5% increase");
    }

    @Test
    public void testHasNotTextPositive() {
        when(element.text()).thenReturn("Something");
        elementAssert.hasNotText("Text which isn't above");
    }

    @Test (expectedExceptions = AssertionError.class)
    public void testHasNotTextNegative() {
        when(element.text()).thenReturn("Something written here");
        elementAssert.hasNotText("Something");
    }

    @Test
    public void testHasTextWithSpecialCharactersInElement() {
        String textWithStringFormatError = "%A";
        when(element.text()).thenReturn(textWithStringFormatError);
        elementAssert.hasText(textWithStringFormatError);
    }

    @Test
    public void testHasNoRaceConditioninHasText() {
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
    public void testHasMultipleClassesOk() {
        when(element.attribute("class")).thenReturn("yolokitten mark");
        elementAssert.hasClass("mark");
    }
}
