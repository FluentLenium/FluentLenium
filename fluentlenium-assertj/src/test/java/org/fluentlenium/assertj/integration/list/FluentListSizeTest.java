package org.fluentlenium.assertj.integration.list;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentListSizeTest extends IntegrationTest {

    @Test
    public void testHasSizeOk() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize(9);
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeKo() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize(10);
    }

    @Test
    public void testHasSizeLessThanOk() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().lessThan(10);
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeLessThanKo() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().lessThan(9);
    }

    @Test
    public void testHasSizeLessThanOrEqualToOk() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().lessThanOrEqualTo(9);
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeLessThanOrEqualToKo() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().lessThanOrEqualTo(8);
    }

    @Test
    public void testHasSizeGreaterThanOk() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().greaterThan(8);
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeGreaterThanKo() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().greaterThan(10);
    }

    @Test
    public void testHasSizeGreaterThanOrEqualToOk() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().greaterThanOrEqualTo(8);
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeGreaterThanOrEqualToKo() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().greaterThanOrEqualTo(10);
    }

}
