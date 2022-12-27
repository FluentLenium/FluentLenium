package io.fluentlenium.core.filter.matcher;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link EndsWithMatcher}.
 */
public class EndsWithMatcherTest {

    @Test
    public void shouldEndWithString() {
        EndsWithMatcher matcher = new EndsWithMatcher("me value");

        assertThat(matcher.isSatisfiedBy("some value")).isTrue();
    }

    @Test
    public void shouldNotEndWithString() {
        EndsWithMatcher matcher = new EndsWithMatcher("some value");

        assertThat(matcher.isSatisfiedBy("non-matching")).isFalse();
    }

    @Test
    public void shouldEndWithPattern() {
        EndsWithMatcher matcher = new EndsWithMatcher(Pattern.compile("me value.*"));

        assertThat(matcher.isSatisfiedBy("some value")).isTrue();
    }

    @Test
    public void shouldNotEndWithPattern() {
        EndsWithMatcher matcher = new EndsWithMatcher(Pattern.compile("value.*"));

        assertThat(matcher.isSatisfiedBy("non-matching")).isFalse();
    }
}
