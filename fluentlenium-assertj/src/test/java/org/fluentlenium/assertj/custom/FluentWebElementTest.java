package org.fluentlenium.assertj.custom;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class FluentWebElementTest {
    @Mock
    FluentWebElement fluentWebElement;
    FluentWebElementAssert fluentWebElementAssert;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        fluentWebElementAssert = FluentLeniumAssertions.assertThat(fluentWebElement);
    }

    @Test
    public void testIsEnabledOk() {
        when(fluentWebElement.enabled()).thenReturn(true);
        fluentWebElementAssert.isEnabled();
    }

    @Test(expected = AssertionError.class)
    public void testIsEnabledKo() throws Exception {
        when(fluentWebElement.enabled()).thenReturn(false);
        fluentWebElementAssert.isEnabled();
    }

    @Test
    public void testIsNotEnabledOk() throws Exception {
        when(fluentWebElement.enabled()).thenReturn(false);
        fluentWebElementAssert.isNotEnabled();
        assertTrue(true);
    }

    @Test(expected = AssertionError.class)
    public void testIsNotEnabledKo() throws Exception {
        when(fluentWebElement.enabled()).thenReturn(true);
        fluentWebElementAssert.isNotEnabled();
    }

    @Test
    public void testIsDisplayedOk() throws Exception {
        when(fluentWebElement.displayed()).thenReturn(true);
        fluentWebElementAssert.isDisplayed();
        assertTrue(true);
    }

    @Test(expected = AssertionError.class)
    public void testIsDisplayedKo() throws Exception {
        when(fluentWebElement.displayed()).thenReturn(false);
        fluentWebElementAssert.isDisplayed();
    }

    @Test
    public void testIsNotDisplayed() throws Exception {
        when(fluentWebElement.displayed()).thenReturn(false);
        fluentWebElementAssert.isNotDisplayed();
        assertTrue(true);
    }

    @Test(expected = AssertionError.class)
    public void testIsSelected() throws Exception {
        when(fluentWebElement.displayed()).thenReturn(true);
        fluentWebElementAssert.isNotDisplayed();
    }

    @Test
    public void testIsNotSelectedOk() throws Exception {
        when(fluentWebElement.selected()).thenReturn(false);
        fluentWebElementAssert.isNotSelected();
        assertTrue(true);
    }

    @Test(expected = AssertionError.class)
    public void testIsNotSelectedKo() throws Exception {
        when(fluentWebElement.selected()).thenReturn(true);
        fluentWebElementAssert.isNotSelected();
    }

    @Test
    public void testIsSelectedOk() throws Exception {
        when(fluentWebElement.selected()).thenReturn(true);
        fluentWebElementAssert.isSelected();
        assertTrue(true);
    }

    @Test(expected = AssertionError.class)
    public void testIsSelectedKo() throws Exception {
        when(fluentWebElement.selected()).thenReturn(false);
        fluentWebElementAssert.isSelected();
    }

    @Test
    public void testHasIdOk() throws Exception {
        when(fluentWebElement.id()).thenReturn("some id");
        fluentWebElementAssert.hasId("some id");
        assertTrue(true);
    }

    @Test(expected = AssertionError.class)
    public void testHasIdKo() throws Exception {
        when(fluentWebElement.id()).thenReturn("other id");
        fluentWebElementAssert.hasId("some id");
    }

    @Test
    public void testHasClassOk() throws Exception {
        when(fluentWebElement.attribute("class")).thenReturn("some-class");
        fluentWebElementAssert.hasClass("some-class");
    }

    @Test(expected = AssertionError.class)
    public void testHasClassKo() throws Exception {
        when(fluentWebElement.attribute("class")).thenReturn("other-class");
        fluentWebElementAssert.hasClass("some-class");
    }

    @Test(expected = AssertionError.class)
    public void testSubstringKo() throws Exception {
        when(fluentWebElement.attribute("class")).thenReturn("yolokitten");
        fluentWebElementAssert.hasClass("yolo");
    }

    @Test
    public void testHasTextOk() throws Exception {
        when(fluentWebElement.text()).thenReturn("There is a 5% increase");
        fluentWebElementAssert.hasText("There is a 5% increase");
    }

    @Test
    public void testHasTextWithSpecialCharactersInElement() throws Exception {
        String textWithStringFormatError = "%A";
        when(fluentWebElement.text()).thenReturn(textWithStringFormatError);
        fluentWebElementAssert.hasText(textWithStringFormatError);
    }

    @Test(expected = AssertionError.class)
    public void testHasTextWithSpecialCharactersInAssertion() throws Exception {
        String textWithStringFormatError = "%A";
        when(fluentWebElement.text()).thenReturn("someText");
        fluentWebElementAssert.hasText(textWithStringFormatError);
    }

    @Test
    public void testHasMultipleClassesOk() throws Exception {
        when(fluentWebElement.attribute("class")).thenReturn("yolokitten mark");
        fluentWebElementAssert.hasClass("mark");
    }
}
