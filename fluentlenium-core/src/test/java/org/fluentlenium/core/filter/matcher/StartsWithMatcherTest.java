package org.fluentlenium.core.filter.matcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Unit test for {@link StartsWithMatcher}.
 */
public class StartsWithMatcherTest {

    @Test
    public void shouldStartWithString() {
        StartsWithMatcher matcher = new StartsWithMatcher("some va");

        assertThat(matcher.isSatisfiedBy("some value")).isTrue();
    }

    @Test
    public void shouldNotStartWithString() {
        StartsWithMatcher matcher = new StartsWithMatcher("some value");

        assertThat(matcher.isSatisfiedBy("val")).isFalse();
    }

    @Test
    public void shouldStartWithPattern() {
        StartsWithMatcher matcher = new StartsWithMatcher(Pattern.compile("^some val"));

        assertThat(matcher.isSatisfiedBy("some value")).isTrue();
    }

    @Test
    public void shouldNotStartWithPattern() {
        StartsWithMatcher matcher = new StartsWithMatcher(Pattern.compile("value.*"));

        assertThat(matcher.isSatisfiedBy("non-matching")).isFalse();
    }
}
