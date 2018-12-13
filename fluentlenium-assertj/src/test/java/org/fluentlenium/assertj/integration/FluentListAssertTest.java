package org.fluentlenium.assertj.integration;

import org.fluentlenium.assertj.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentListAssertTest extends IntegrationFluentTest {

    @Test
    public void testHasTextOk() {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasText("Paul");
    }

    @Test(expected = AssertionError.class)
    public void testHasTextKo() {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasText("John");
    }

    @Test
    public void testHasNotTextOk() {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasNotText("John");
    }

    @Test(expected = AssertionError.class)
    public void testHasNotTextKo() {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasNotText("Paul");
    }

    @Test
    public void testHasSizeOk() {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize(9);
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeKo() {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize(10);
    }

    @Test
    public void testHasSizeLessThanOk() {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().lessThan(10);
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeLessThanKo() {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().lessThan(9);
    }

    @Test
    public void testHasSizeLessThanOrEqualToOk() {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().lessThanOrEqualTo(9);
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeLessThanOrEqualToKo() {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().lessThanOrEqualTo(8);
    }

    @Test
    public void testHasSizeGreaterThanOk() {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().greaterThan(8);
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeGreaterThanKo() {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().greaterThan(10);
    }

    @Test
    public void testHasSizeGreaterThanOrEqualToOk() {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().greaterThanOrEqualTo(8);
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeGreaterThanOrEqualToKo() {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().greaterThanOrEqualTo(10);
    }

    @Test
    public void testAssertOnOneOfManyClasses() {
        goTo(DEFAULT_URL);
        assertThat(find("#multiple-css-class")).hasClass("class1");
    }

    @Test(expected = AssertionError.class)
    public void testAssertOnSubstringOfAClass() {
        goTo(DEFAULT_URL);
        assertThat(find("#multiple-css-class")).hasClass("cla");
    }
}
