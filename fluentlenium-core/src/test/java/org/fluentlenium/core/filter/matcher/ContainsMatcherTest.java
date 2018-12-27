package org.fluentlenium.core.filter.matcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Unit test for {@link ContainsMatcher}.
 */
public class ContainsMatcherTest {

    @Test
    public void shouldContainString() {
        ContainsMatcher matcher = new ContainsMatcher("me value");

        assertThat(matcher.isSatisfiedBy("some value")).isTrue();
    }

    @Test
    public void shouldNotContainString() {
        ContainsMatcher matcher = new ContainsMatcher("some value");

        assertThat(matcher.isSatisfiedBy("non-matching")).isFalse();
    }

    @Test
    public void shouldContainPattern() {
        ContainsMatcher matcher = new ContainsMatcher(Pattern.compile("me value.*"));

        assertThat(matcher.isSatisfiedBy("some value")).isTrue();
    }

    @Test
    public void shouldNotContainPattern() {
        ContainsMatcher matcher = new ContainsMatcher(Pattern.compile("value.*"));

        assertThat(matcher.isSatisfiedBy("non-matching")).isFalse();
    }
}
