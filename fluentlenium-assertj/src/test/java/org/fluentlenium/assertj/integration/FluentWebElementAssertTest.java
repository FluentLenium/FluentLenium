package org.fluentlenium.assertj.integration;

import org.fluentlenium.assertj.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementAssertTest extends IntegrationFluentTest {

    @Test
    public void testIsEnabledOk() {
        goTo(DEFAULT_URL);
        assertThat(el("#name")).isEnabled();
    }

    @Test(expected = AssertionError.class)
    public void testIsEnabledKo() {
        goTo(DEFAULT_URL);
        assertThat(el("#disabled")).isEnabled();
    }

    @Test
    public void testIsNotEnabledOk() {
        goTo(DEFAULT_URL);
        assertThat(el("#disabled")).isNotEnabled();
    }

    @Test(expected = AssertionError.class)
    public void testIsNotEnabledKo() {
        goTo(DEFAULT_URL);
        assertThat(el("#name")).isNotEnabled();

    }

    @Test
    public void testIsDisplayedOk() {
        goTo(DEFAULT_URL);
        assertThat(el("#disabled")).isDisplayed();
    }

    @Test(expected = AssertionError.class)
    public void testIsDisplayedKo() {
        goTo(DEFAULT_URL);
        executeScript("document.getElementById(\"disabled\").style.display=\"none\";");
        assertThat(el("#disabled")).isDisplayed();
    }

    @Test
    public void testIsNotDisplayed() {
        goTo(DEFAULT_URL);
        executeScript("document.getElementById(\"disabled\").style.display=\"none\";");
        assertThat(el("#disabled")).isNotDisplayed();

    }

    @Test(expected = AssertionError.class)
    public void testIsSelected() {
        goTo(DEFAULT_URL);
        assertThat(el("#disabled")).isNotDisplayed();
    }

    @Test
    public void testIsNotSelectedOk() {
        goTo(DEFAULT_URL);
        assertThat(el("#disabled")).isNotSelected();
    }

    @Test(expected = AssertionError.class)
    public void testIsNotSelectedKo() {
        goTo(DEFAULT_URL);
        assertThat(el("#selected")).isNotSelected();
    }

    @Test
    public void testIsSelectedOk() {
        goTo(DEFAULT_URL);
        assertThat(el("#selected")).isSelected();
    }

    @Test(expected = AssertionError.class)
    public void testIsSelectedKo() {
        goTo(DEFAULT_URL);
        assertThat(el("#disabled")).isSelected();
    }

    @Test
    public void testHasTestOk() {
        goTo(DEFAULT_URL);
        assertThat(el("#location")).hasText("Pharmacy");
    }

    @Test(expected = AssertionError.class)
    public void testHasTestKo() {
        goTo(DEFAULT_URL);
        assertThat(el("#location")).hasText("Drugstore");
    }

    @Test
    public void testHasTestMatchingOk() {
        goTo(DEFAULT_URL);
        assertThat(el("#location")).hasTextMatching("Pha\\w+cy");
    }

    @Test(expected = AssertionError.class)
    public void testHasTestMatchingKo() {
        goTo(DEFAULT_URL);
        executeScript("document.getElementById(\"location\").innerHTML=\"Pha rmacy\";");
        assertThat(el("#location")).hasTextMatching("Pha\\w+cy");
    }

    @Test
    public void testAssertOnOneOfManyClasses() {
        goTo(DEFAULT_URL);
        assertThat(el("#multiple-css-class")).hasClass("class1");
    }

    @Test(expected = AssertionError.class)
    public void testAssertOnSubstringOfAClass() {
        goTo(DEFAULT_URL);
        assertThat(el("#multiple-css-class")).hasClass("cla");
    }
}
