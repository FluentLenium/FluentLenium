package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementAssertTest extends LocalFluentCase {

    @Test
    public void testIsEnabledOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#name")).isEnabled();
    }

    @Test(expected = AssertionError.class)
    public void testIsEnabledKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#disabled")).isEnabled();
    }


    @Test
    public void testIsNotEnabledOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#disabled")).isNotEnabled();
    }


    @Test(expected = AssertionError.class)
    public void testIsNotEnabledKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#name")).isNotEnabled();

    }

    @Test
    public void testIsDisplayedOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#disabled")).isDisplayed();
    }

    @Test(expected = AssertionError.class)
    public void testIsDisplayedKo() throws Exception {
        goTo(DEFAULT_URL);
        executeScript("document.getElementById(\"disabled\").style.display=\"none\";");
        assertThat(findFirst("#disabled")).isDisplayed();
    }

    @Test
    public void testIsNotDisplayed() throws Exception {
        goTo(DEFAULT_URL);
        executeScript("document.getElementById(\"disabled\").style.display=\"none\";");
        assertThat(findFirst("#disabled")).isNotDisplayed();

    }

    @Test(expected = AssertionError.class)
    public void testIsSelected() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#disabled")).isNotDisplayed();
    }

    @Test
    public void testIsNotSelectedOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#disabled")).isNotSelected();
    }

    @Test(expected = AssertionError.class)
    public void testIsNotSelectedKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#selected")).isNotSelected();

    }

    @Test
    public void testIsSelectedOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#selected")).isSelected();

    }

    @Test(expected = AssertionError.class)
    public void testIsSelectedKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#disabled")).isSelected();
    }

    @Test
    public void testHasTestOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#location")).hasText("Pharmacy");
    }

    @Test(expected = AssertionError.class)
    public void testHasTestKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#location")).hasText("Drugstore");
    }

    @Test
    public void testHasTestMatchingOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#location")).hasTextMatching("Pha\\w+cy");
    }

    @Test(expected = AssertionError.class)
    public void testHasTestMatchingKo() throws Exception {
        goTo(DEFAULT_URL);
        executeScript("document.getElementById(\"location\").innerHTML=\"Pha rmacy\";");
        assertThat(findFirst("#location")).hasTextMatching("Pha\\w+cy");
    }

    @Test
    public void testAssertOnOneOfManyClasses() {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#multiple-css-class")).hasClass("class1");
    }

    @Test(expected = AssertionError.class)
    public void testAssertOnSubstringOfAClass() {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#multiple-css-class")).hasClass("cla");
    }
}
