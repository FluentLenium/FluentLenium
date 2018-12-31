package org.fluentlenium.assertj.custom;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.Arrays;
import java.util.List;

public class FluentListAssertTest<E extends FluentWebElement> {

    @Mock
    private FluentList<E> fluentList;

    private FluentListAssert listAssert;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        listAssert = FluentLeniumAssertions.assertThat(fluentList);
    }

    @Test
    public void testHasText() {
        when(fluentList.texts()).thenReturn(singletonList("some text"));
        assertNotNull(listAssert.hasText("some text"));
    }

    @Test
    public void testHasTextWithMissingText() {
        when(fluentList.texts()).thenReturn(List.of("some text", "other text"));

        assertThatThrownBy(() -> listAssert.hasText("absent text"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("No selected elements contains text: absent text . Actual texts found: [some text, other text]");
    }

    @Test
    public void testHasNotText() {
        when(fluentList.texts()).thenReturn(singletonList("other text"));
        assertNotNull(listAssert.hasNotText("some text"));
    }

    @Test
    public void testHasNotTextWithTextPresent() {
        when(fluentList.texts()).thenReturn(List.of("some text", "other text"));

        assertThatThrownBy(() -> listAssert.hasNotText("other text"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("At least one selected elements contains text: other text ."
                        + " Actual texts found: [some text, other text]");
    }

    @Test
    public void testHasSizeOk() {
        when(fluentList.size()).thenReturn(7);
        assertNotNull(listAssert.hasSize(7));
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeKo() {
        when(fluentList.size()).thenReturn(7);
        listAssert.hasSize(5);
    }

    @Test
    public void testHasSizeLessThanOk() {
        when(fluentList.size()).thenReturn(7);
        assertNotNull(listAssert.hasSize().lessThan(9));
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeLessThanKo() {
        when(fluentList.size()).thenReturn(7);
        listAssert.hasSize().lessThan(7);
        listAssert.hasSize().lessThan(6);
    }

    @Test
    public void testHasSizeLessThanOrEqualToOk() {
        when(fluentList.size()).thenReturn(7);
        assertNotNull(listAssert.hasSize().lessThanOrEqualTo(7));
        assertNotNull(listAssert.hasSize().lessThanOrEqualTo(8));
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeLessThanOrEqualToKo() {
        when(fluentList.size()).thenReturn(7);
        listAssert.hasSize().lessThanOrEqualTo(6);
    }

    @Test
    public void testHasSizeGreaterThanOk() {
        when(fluentList.size()).thenReturn(7);
        assertNotNull(listAssert.hasSize().greaterThan(6));
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeGreaterThanKo() {
        when(fluentList.size()).thenReturn(7);
        listAssert.hasSize().greaterThan(7);
        listAssert.hasSize().greaterThan(8);
    }

    @Test
    public void testHasSizeGreaterThanOrEqualToOk() {
        when(fluentList.size()).thenReturn(7);
        assertNotNull(listAssert.hasSize().greaterThanOrEqualTo(7));
        assertNotNull(listAssert.hasSize().greaterThanOrEqualTo(6));
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeGreaterThanOrEqualToKo() {
        when(fluentList.size()).thenReturn(7);
        listAssert.hasSize().greaterThanOrEqualTo(8);
    }

    @Test
    public void testHasIdOk() {
        when(fluentList.ids()).thenReturn(singletonList("some-id"));
        listAssert.hasId("some-id");
    }

    @Test(expected = AssertionError.class)
    public void testHasIdKo() {
        when(fluentList.ids()).thenReturn(singletonList("other-id"));
        listAssert.hasId("some-id");
    }

    @Test(expected = AssertionError.class)
    public void testHasIdEmptyKo() {
        when(fluentList.ids()).thenReturn(emptyList());
        listAssert.hasId("some-id");
    }

    @Test
    public void testHasClassOk() {
        when(fluentList.attributes("class")).thenReturn(singletonList("some-class"));
        listAssert.hasClass("some-class");
    }

    @Test(expected = AssertionError.class)
    public void testHasClassKo() {
        when(fluentList.attributes("class")).thenReturn(singletonList("other-class"));
        listAssert.hasClass("some-class");
    }

    @Test(expected = AssertionError.class)
    public void testHasClassEmptyKo() {
        when(fluentList.attributes("class")).thenReturn(emptyList());
        listAssert.hasClass("some-class");
    }

    @Test(expected = AssertionError.class)
    public void testSubstringKo() {
        when(fluentList.attributes("class")).thenReturn(singletonList("yolokitten"));
        listAssert.hasClass("yolo");
    }

    @Test
    public void testHasMultipleClassesOk() {
        when(fluentList.attributes("class")).thenReturn(singletonList("yolokitten mark"));
        listAssert.hasClass("mark");
    }

    @Test
    public void testHasMultipleClassesOkBanana() {
        when(fluentList.attributes("class")).thenReturn(Arrays.asList("beta product", "alpha male"));
        listAssert.hasClass("male");
    }
}
