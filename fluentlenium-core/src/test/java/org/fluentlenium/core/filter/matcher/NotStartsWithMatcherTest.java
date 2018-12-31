package org.fluentlenium.core.filter.matcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Unit test for {@link NotStartsWithMatcher}.
 */
public class NotStartsWithMatcherTest {

    @Test
    public void shouldNotStartWithString() {
        NotStartsWithMatcher matcher = new NotStartsWithMatcher("some value");

        assertThat(matcher.isSatisfiedBy("val")).isTrue();
    }

    @Test
    public void shouldStartWithString() {
        NotStartsWithMatcher matcher = new NotStartsWithMatcher("some va");

        assertThat(matcher.isSatisfiedBy("some value")).isFalse();
    }

    @Test
    public void shouldNotStartWithPattern() {
        NotStartsWithMatcher matcher = new NotStartsWithMatcher(Pattern.compile("value.*"));

        assertThat(matcher.isSatisfiedBy("non-matching")).isTrue();
    }

    @Test
    public void shouldStartWithPattern() {
        NotStartsWithMatcher matcher = new NotStartsWithMatcher(Pattern.compile("^some val"));

        assertThat(matcher.isSatisfiedBy("some value")).isFalse();
    }
}
