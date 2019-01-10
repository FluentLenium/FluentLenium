package org.fluentlenium.assertj.custom;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.Dimension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

public class FluentWebElementTest {
    @Mock
    private FluentWebElement element;
    private FluentWebElementAssert elementAssert;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        elementAssert = FluentLeniumAssertions.assertThat(element);
    }

    @Test
    public void testIsEnabledOk() {
        when(element.enabled()).thenReturn(true);
        elementAssert.isEnabled();
    }

    @Test(expected = AssertionError.class)
    public void testIsEnabledKo() {
        when(element.enabled()).thenReturn(false);
        elementAssert.isEnabled();
    }

    @Test
    public void testIsNotEnabledOk() {
        when(element.enabled()).thenReturn(false);
        elementAssert.isNotEnabled();
    }

    @Test(expected = AssertionError.class)
    public void testIsNotEnabledKo() {
        when(element.enabled()).thenReturn(true);
        elementAssert.isNotEnabled();
    }

    @Test
    public void testIsClickableOk() {
        when(element.clickable()).thenReturn(true);
        elementAssert.isClickable();
    }

    @Test(expected = AssertionError.class)
    public void testIsClickabledKo() {
        when(element.clickable()).thenReturn(false);
        elementAssert.isClickable();
    }

    @Test
    public void testIsNotClickabledOk() {
        when(element.clickable()).thenReturn(false);
        elementAssert.isNotClickable();
    }

    @Test(expected = AssertionError.class)
    public void testIsNotClickabledKo() {
        when(element.clickable()).thenReturn(true);
        elementAssert.isNotClickable();
    }

    @Test
    public void testIsDisplayedOk() {
        when(element.displayed()).thenReturn(true);
        elementAssert.isDisplayed();
    }

    @Test(expected = AssertionError.class)
    public void testIsDisplayedKo() {
        when(element.displayed()).thenReturn(false);
        elementAssert.isDisplayed();
    }

    @Test
    public void testIsNotDisplayed() {
        when(element.displayed()).thenReturn(false);
        elementAssert.isNotDisplayed();
    }

    @Test(expected = AssertionError.class)
    public void testIsSelected() {
        when(element.displayed()).thenReturn(true);
        elementAssert.isNotDisplayed();
    }

    @Test
    public void testIsNotSelectedOk() {
        when(element.selected()).thenReturn(false);
        elementAssert.isNotSelected();
    }

    @Test(expected = AssertionError.class)
    public void testIsNotSelectedKo() {
        when(element.selected()).thenReturn(true);
        elementAssert.isNotSelected();
    }

    @Test
    public void testIsSelectedOk() {
        when(element.selected()).thenReturn(true);
        elementAssert.isSelected();
    }

    @Test(expected = AssertionError.class)
    public void testIsSelectedKo() {
        when(element.selected()).thenReturn(false);
        elementAssert.isSelected();
    }

    @Test
    public void testHasNameOk() {
        when(element.name()).thenReturn("some name");
        elementAssert.hasName("some name");
    }

    @Test(expected = AssertionError.class)
    public void testHasNameKo() {
        when(element.name()).thenReturn("other name");
        elementAssert.hasName("some name");
    }

    @Test
    public void testHasValueOk() {
        when(element.value()).thenReturn("some value");
        elementAssert.hasValue("some value");
    }

    @Test(expected = AssertionError.class)
    public void testHasValueKo() {
        when(element.value()).thenReturn("other value");
        elementAssert.hasValue("some value");
    }

    @Test
    public void testHasTagNameOk() {
        when(element.tagName()).thenReturn("some tag");
        elementAssert.hasTagName("some tag");
    }

    @Test(expected = AssertionError.class)
    public void testHasTagNameKo() {
        when(element.tagName()).thenReturn("other tag");
        elementAssert.hasTagName("some tag");
    }

    @Test
    public void testHasPropertyValueOk() {
        when(element.attribute("attribute")).thenReturn("some value");
        elementAssert.hasAttributeValue("attribute", "some value");
    }

    @Test(expected = AssertionError.class)
    public void testHasPropertyValueKo() {
        when(element.attribute("attribute")).thenReturn("other value");
        elementAssert.hasAttributeValue("attribute", "some value");
    }

    @Test
    public void testHasIdOk() {
        when(element.id()).thenReturn("some id");
        elementAssert.hasId("some id");
    }

    @Test(expected = AssertionError.class)
    public void testHasIdKo() {
        when(element.id()).thenReturn("other id");
        elementAssert.hasId("some id");
    }

    @Test
    public void testHasDimensionOk() {
        when(element.size()).thenReturn(new Dimension(1, 2));
        elementAssert.hasDimension(new Dimension(1, 2));
    }

    @Test(expected = AssertionError.class)
    public void testHasDimensionKo() {
        when(element.size()).thenReturn(new Dimension(2, 1));
        elementAssert.hasDimension(new Dimension(1, 2));
    }

    @Test
    public void testHasClassOk() {
        when(element.attribute("class")).thenReturn("some-class");
        elementAssert.hasClass("some-class");
    }

    @Test(expected = AssertionError.class)
    public void testHasClassKo() {
        when(element.attribute("class")).thenReturn("other-class");
        elementAssert.hasClass("some-class");
    }

    @Test(expected = AssertionError.class)
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

    @Test (expected = AssertionError.class)
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
