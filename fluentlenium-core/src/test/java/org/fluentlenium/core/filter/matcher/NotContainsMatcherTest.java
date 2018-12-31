package org.fluentlenium.core.filter.matcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Unit test for {@link NotContainsMatcher}.
 */
public class NotContainsMatcherTest {

    @Test
    public void shouldNotContainString() {
        NotContainsMatcher matcher = new NotContainsMatcher("some value");

        assertThat(matcher.isSatisfiedBy("non-matching")).isTrue();
    }

    @Test
    public void shouldContainString() {
        NotContainsMatcher matcher = new NotContainsMatcher("me value");

        assertThat(matcher.isSatisfiedBy("some value")).isFalse();
    }

    @Test
    public void shouldNotContainPattern() {
        NotContainsMatcher matcher = new NotContainsMatcher(Pattern.compile("value.*"));

        assertThat(matcher.isSatisfiedBy("non-matching")).isTrue();
    }

    @Test
    public void shouldContainPattern() {
        NotContainsMatcher matcher = new NotContainsMatcher(Pattern.compile("me value.*"));

        assertThat(matcher.isSatisfiedBy("some value")).isFalse();
    }
}
