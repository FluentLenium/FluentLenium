package io.fluentlenium.core.filter.matcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Unit test for {@link ContainsWordMatcher}.
 */
public class ContainsWordMatcherTest {

    @Test
    public void shouldContainWord() {
        ContainsWordMatcher matcher = new ContainsWordMatcher("value");

        assertThat(matcher.isSatisfiedBy("some value")).isTrue();
    }

    @Test
    public void shouldNotContainWord() {
        ContainsWordMatcher matcher = new ContainsWordMatcher("some value");

        assertThat(matcher.isSatisfiedBy("non-matching")).isFalse();
    }
}
