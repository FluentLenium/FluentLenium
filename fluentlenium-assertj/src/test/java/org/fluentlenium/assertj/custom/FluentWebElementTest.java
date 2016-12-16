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
    public void testIsEnabledKo() throws Exception {
        when(element.enabled()).thenReturn(false);
        elementAssert.isEnabled();
    }

    @Test
    public void testIsNotEnabledOk() throws Exception {
        when(element.enabled()).thenReturn(false);
        elementAssert.isNotEnabled();
    }

    @Test(expected = AssertionError.class)
    public void testIsNotEnabledKo() throws Exception {
        when(element.enabled()).thenReturn(true);
        elementAssert.isNotEnabled();
    }

    @Test
    public void testIsDisplayedOk() throws Exception {
        when(element.displayed()).thenReturn(true);
        elementAssert.isDisplayed();
    }

    @Test(expected = AssertionError.class)
    public void testIsDisplayedKo() throws Exception {
        when(element.displayed()).thenReturn(false);
        elementAssert.isDisplayed();
    }

    @Test
    public void testIsNotDisplayed() throws Exception {
        when(element.displayed()).thenReturn(false);
        elementAssert.isNotDisplayed();
    }

    @Test(expected = AssertionError.class)
    public void testIsSelected() throws Exception {
        when(element.displayed()).thenReturn(true);
        elementAssert.isNotDisplayed();
    }

    @Test
    public void testIsNotSelectedOk() throws Exception {
        when(element.selected()).thenReturn(false);
        elementAssert.isNotSelected();
    }

    @Test(expected = AssertionError.class)
    public void testIsNotSelectedKo() throws Exception {
        when(element.selected()).thenReturn(true);
        elementAssert.isNotSelected();
    }

    @Test
    public void testIsSelectedOk() throws Exception {
        when(element.selected()).thenReturn(true);
        elementAssert.isSelected();
    }

    @Test(expected = AssertionError.class)
    public void testIsSelectedKo() throws Exception {
        when(element.selected()).thenReturn(false);
        elementAssert.isSelected();
    }

    @Test
    public void testHasIdOk() throws Exception {
        when(element.id()).thenReturn("some id");
        elementAssert.hasId("some id");
    }

    @Test(expected = AssertionError.class)
    public void testHasIdKo() throws Exception {
        when(element.id()).thenReturn("other id");
        elementAssert.hasId("some id");
    }

    @Test
    public void testHasClassOk() throws Exception {
        when(element.attribute("class")).thenReturn("some-class");
        elementAssert.hasClass("some-class");
    }

    @Test(expected = AssertionError.class)
    public void testHasClassKo() throws Exception {
        when(element.attribute("class")).thenReturn("other-class");
        elementAssert.hasClass("some-class");
    }

    @Test(expected = AssertionError.class)
    public void testSubstringKo() throws Exception {
        when(element.attribute("class")).thenReturn("yolokitten");
        elementAssert.hasClass("yolo");
    }

    @Test
    public void testHasTextOk() throws Exception {
        when(element.text()).thenReturn("There is a 5% increase");
        elementAssert.hasText("There is a 5% increase");
    }

    @Test
    public void testHasTextWithSpecialCharactersInElement() throws Exception {
        String textWithStringFormatError = "%A";
        when(element.text()).thenReturn(textWithStringFormatError);
        elementAssert.hasText(textWithStringFormatError);
    }

    @Test(expected = AssertionError.class)
    public void testHasTextWithSpecialCharactersInAssertion() throws Exception {
        String textWithStringFormatError = "%A";
        when(element.text()).thenReturn("someText");
        elementAssert.hasText(textWithStringFormatError);
    }

    @Test
    public void testHasNoRaceConditioninHasText() throws Exception {
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
    public void testHasMultipleClassesOk() throws Exception {
        when(element.attribute("class")).thenReturn("yolokitten mark");
        elementAssert.hasClass("mark");
    }
}
