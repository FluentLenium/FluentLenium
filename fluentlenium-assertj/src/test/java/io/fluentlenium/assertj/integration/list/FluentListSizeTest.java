package io.fluentlenium.assertj.integration.list;

import io.fluentlenium.assertj.AssertionTestSupport;
import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentListSizeTest extends IntegrationTest {

    @Test
    public void testHasSizeOk() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasSize(9);
    }

    @Test
    public void testHasSizeKo() {
        goTo(DEFAULT_URL);
        AssertionTestSupport.assertThatAssertionErrorIsThrownBy(
                () -> assertThat($("span")).hasSize(10)
        ).hasMessage("Expected size: 10. Actual size: 9.");
    }

    @Test
    public void testHasSizeLessThanOk() {
        goTo(DEFAULT_URL);
        assertThat($("span")).isNotEmpty();
        assertThat($("span")).hasSize().lessThan(10);
    }

    @Test
    public void testHasSizeLessThanKo() {
        goTo(DEFAULT_URL);
        AssertionTestSupport.assertThatAssertionErrorIsThrownBy(
                () -> assertThat($("span")).hasSize().lessThan(9)
        ).hasMessage("Actual size: 9 is not less than: 9");
    }

    @Test
    public void testHasSizeLessThanOrEqualToOk() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasSize().lessThanOrEqualTo(9);
    }

    @Test
    public void testHasSizeLessThanOrEqualToKo() {
        goTo(DEFAULT_URL);
        AssertionTestSupport.assertThatAssertionErrorIsThrownBy(
                () -> assertThat($("span")).hasSize().lessThanOrEqualTo(8)
        ).hasMessage("Actual size: 9 is not less than or equal to: 8");
    }

    @Test
    public void testHasSizeGreaterThanOk() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasSize().greaterThan(8);
    }

    @Test
    public void testHasSizeGreaterThanKo() {
        goTo(DEFAULT_URL);
        AssertionTestSupport.assertThatAssertionErrorIsThrownBy(
                () -> assertThat($("span")).hasSize().greaterThan(10)
        ).hasMessage("Actual size: 9 is not greater than: 10");
    }

    @Test
    public void testHasSizeGreaterThanOrEqualToOk() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasSize().greaterThanOrEqualTo(8);
    }

    @Test
    public void testHasSizeGreaterThanOrEqualToKo() {
        goTo(DEFAULT_URL);
        AssertionTestSupport.assertThatAssertionErrorIsThrownBy(
                () -> assertThat($("span")).hasSize().greaterThanOrEqualTo(10)
        ).hasMessage("Actual size: 9 is not greater than or equal to: 10");
    }

}
