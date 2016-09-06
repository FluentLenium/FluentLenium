package org.fluentlenium.core.filter.matcher;


import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class CaculateServiceTest {

    @Test
    public void checkSimpleEqualOk() {
        assertThat(CalculateService.equal(null, "toto", "toto")).isTrue();
    }

    @Test
    public void checkSimpleEqualNok() {
        assertThat(CalculateService.equal(null, "toto", "tot")).isFalse();
    }

    @Test
    public void checkPatternEqualOk() {
        assertThat(CalculateService.equal(Pattern.compile("[to]*"), null, "toto")).isTrue();

    }

    @Test
    public void checkPatternEqualNok() {
        assertThat(CalculateService.equal(Pattern.compile("[to]?"), null, "tot")).isFalse();
    }

    @Test
    public void checkSimpleContainsOk() {
        assertThat(CalculateService.contains(null, "to", "toto")).isTrue();
    }

    @Test
    public void checkSimpleContainsNok() {
        assertThat(CalculateService.contains(null, "toto", "ecole")).isFalse();
    }

    @Test
    public void checkPatternContainsOk() {
        assertThat(CalculateService.contains(Pattern.compile("[to]*"), null, "toto")).isTrue();

    }

    @Test
    public void checkPatternContainsNok() {
        assertThat(CalculateService.contains(Pattern.compile("[ta]*]"), null, "tot")).isFalse();
    }

    @Test
    public void checkSimpleStartsWithOk() {
        assertThat(CalculateService.startsWith(null, "to", "toto")).isTrue();
    }

    @Test
    public void checkSimpleStartsWithNok() {
        assertThat(CalculateService.startsWith(null, "to", "la to to")).isFalse();
    }

    @Test
    public void checkPatternStartsWithOk() {
        assertThat(CalculateService.startsWith(Pattern.compile("[to]*"), null, "toto")).isTrue();

    }

    @Test
    public void checkPatternStartsWithNok() {
        assertThat(CalculateService.startsWith(Pattern.compile("[ta]*]"), null, "tot")).isFalse();
    }

    @Test
    public void checkSimpleEndsWithOk() {
        assertThat(CalculateService.endsWith(null, "to", "toto")).isTrue();
    }

    @Test
    public void checkSimpleEndsWithNok() {
        assertThat(CalculateService.endsWith(null, "la", "la to to")).isFalse();
    }

    @Test
    public void checkPatternEndsWithOk() {
        assertThat(CalculateService.endsWith(Pattern.compile("[to]*"), null, "toto to")).isTrue();

    }

    @Test
    public void checkPatternEndsWithNok() {
        assertThat(CalculateService.endsWith(Pattern.compile("[ta]*]"), null, "ta ta ")).isFalse();
    }
}
