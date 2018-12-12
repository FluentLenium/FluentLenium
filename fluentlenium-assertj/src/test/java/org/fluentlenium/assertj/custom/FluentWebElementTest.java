package org.fluentlenium.assertj.custom;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    @Test(expected = AssertionError.class)
    public void testHasTextWithSpecialCharactersInAssertion() {
        String textWithStringFormatError = "%A";
        when(element.text()).thenReturn("someText");
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
