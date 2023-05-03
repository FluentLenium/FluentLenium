package io.fluentlenium.core.filter.matcher;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link EqualMatcher}.
 */
public class EqualMatcherTest {

    @Test
    public void shouldEqualToString() {
        EndsWithMatcher matcher = new EndsWithMatcher("some value");

        assertThat(matcher.isSatisfiedBy("some value")).isTrue();
    }

    @Test
    public void shouldNotEqualToString() {
        EndsWithMatcher matcher = new EndsWithMatcher("some value");

        assertThat(matcher.isSatisfiedBy("non-matching")).isFalse();
    }

    @Test
    public void shouldEqualToPattern() {
        EndsWithMatcher matcher = new EndsWithMatcher(Pattern.compile("^.*me valu.*"));

        assertThat(matcher.isSatisfiedBy("some value")).isTrue();
    }

    @Test
    public void shouldNotEqualToPattern() {
        EndsWithMatcher matcher = new EndsWithMatcher(Pattern.compile("value.*"));

        assertThat(matcher.isSatisfiedBy("non-matching")).isFalse();
    }
}
