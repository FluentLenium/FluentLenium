package io.fluentlenium.core.filter.matcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Unit test for {@link NotEndsWithMatcher}.
 */
public class NotEndsWithMatcherTest {

    @Test
    public void shouldNotEndWithString() {
        NotEndsWithMatcher matcher = new NotEndsWithMatcher("some value");

        assertThat(matcher.isSatisfiedBy("non-matching")).isTrue();
    }

    @Test
    public void shouldEndWithString() {
        NotEndsWithMatcher matcher = new NotEndsWithMatcher("me value");

        assertThat(matcher.isSatisfiedBy("some value")).isFalse();
    }

    @Test
    public void shouldNotEndWithPattern() {
        NotEndsWithMatcher matcher = new NotEndsWithMatcher(Pattern.compile("value.*"));

        assertThat(matcher.isSatisfiedBy("non-matching")).isTrue();
    }

    @Test
    public void shouldEndWithPattern() {
        NotEndsWithMatcher matcher = new NotEndsWithMatcher(Pattern.compile("me value.*"));

        assertThat(matcher.isSatisfiedBy("some value")).isFalse();
    }
}
