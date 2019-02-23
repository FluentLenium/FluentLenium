package org.fluentlenium.assertj.integration.list;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentListSizeTest extends IntegrationTest {

    @Test
    public void testHasSizeOk() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasSize(9);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testHasSizeKo() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasSize(10);
    }

    @Test
    public void testHasSizeLessThanOk() {
        goTo(DEFAULT_URL);
        assertThat($("span")).isNotEmpty();
        assertThat($("span")).hasSize().lessThan(10);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testHasSizeLessThanKo() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasSize().lessThan(9);
    }

    @Test
    public void testHasSizeLessThanOrEqualToOk() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasSize().lessThanOrEqualTo(9);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testHasSizeLessThanOrEqualToKo() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasSize().lessThanOrEqualTo(8);
    }

    @Test
    public void testHasSizeGreaterThanOk() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasSize().greaterThan(8);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testHasSizeGreaterThanKo() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasSize().greaterThan(10);
    }

    @Test
    public void testHasSizeGreaterThanOrEqualToOk() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasSize().greaterThanOrEqualTo(8);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testHasSizeGreaterThanOrEqualToKo() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasSize().greaterThanOrEqualTo(10);
    }

}
