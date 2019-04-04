package org.fluentlenium.core.conditions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Unit test for {@link StringConditionsImpl}.
 */
public class StringConditionsImplTest {

    //contains

    @Test
    public void shouldReturnTrueIfContainsString() {
        StringConditions stringConditions = new StringConditionsImpl("Some magical text.");

        assertThat(stringConditions.contains("magical")).isTrue();
    }

    @Test
    public void shouldReturnFalseIsNotContainsString() {
        StringConditions stringConditions = new StringConditionsImpl("Some magical text.");

        assertThat(stringConditions.contains("element")).isFalse();
    }

    @Test
    public void shouldReturnFalseForContainsIfTargetStringIsNull() {
        StringConditions stringConditions = new StringConditionsImpl(null);

        assertThat(stringConditions.contains("magical")).isFalse();
    }

    //startsWith

    @Test
    public void shouldReturnTrueIfStartsWithString() {
        StringConditions stringConditions = new StringConditionsImpl("Some magical text.");

        assertThat(stringConditions.startsWith("Some")).isTrue();
    }

    @Test
    public void shouldReturnFalseIsNotStartsWithString() {
        StringConditions stringConditions = new StringConditionsImpl("Some magical text.");

        assertThat(stringConditions.startsWith("magical")).isFalse();
    }

    @Test
    public void shouldReturnFalseForStartsWithIfTargetStringIsNull() {
        StringConditions stringConditions = new StringConditionsImpl(null);

        assertThat(stringConditions.startsWith("magical")).isFalse();
    }

    //endsWith

    @Test
    public void shouldReturnTrueIfEndsWithString() {
        StringConditions stringConditions = new StringConditionsImpl("Some magical text.");

        assertThat(stringConditions.endsWith("text.")).isTrue();
    }

    @Test
    public void shouldReturnFalseIsNotEndsWithString() {
        StringConditions stringConditions = new StringConditionsImpl("Some magical text.");

        assertThat(stringConditions.endsWith("magical")).isFalse();
    }

    @Test
    public void shouldReturnFalseForEndsWithIfTargetStringIsNull() {
        StringConditions stringConditions = new StringConditionsImpl(null);

        assertThat(stringConditions.endsWith("magical")).isFalse();
    }

    //equalTo

    @Test
    public void shouldReturnTrueIfEqualToString() {
        StringConditions stringConditions = new StringConditionsImpl("Some magical text.");

        assertThat(stringConditions.equalTo("Some magical text.")).isTrue();
    }

    @Test
    public void shouldReturnFalseIsNotEqualToString() {
        StringConditions stringConditions = new StringConditionsImpl("Some magical text.");

        assertThat(stringConditions.equalTo("magical")).isFalse();
    }

    @Test
    public void shouldReturnFalseForEqualToIfTargetStringIsNull() {
        StringConditions stringConditions = new StringConditionsImpl(null);

        assertThat(stringConditions.equalTo("magical")).isFalse();
    }

    //equalToIgnoreCase

    @Test
    public void shouldReturnTrueIfEqualToIgnoreCaseString() {
        StringConditions stringConditions = new StringConditionsImpl("Some MAGICAL text.");

        assertThat(stringConditions.equalToIgnoreCase("Some magical text.")).isTrue();
    }

    @Test
    public void shouldReturnFalseIsNotEqualToIgnoreCaseString() {
        StringConditions stringConditions = new StringConditionsImpl("Some MAGICAL text.");

        assertThat(stringConditions.equalToIgnoreCase("magical")).isFalse();
    }

    @Test
    public void shouldReturnFalseForEqualToIgnoreCaseIfTargetStringIsNull() {
        StringConditions stringConditions = new StringConditionsImpl(null);

        assertThat(stringConditions.equalToIgnoreCase("magical")).isFalse();
    }

    //matches

    @Test
    public void shouldReturnTrueIfMatchesString() {
        StringConditions stringConditions = new StringConditionsImpl("Some magical text.");

        assertThat(stringConditions.matches("^Some .* text.$")).isTrue();
    }

    @Test
    public void shouldReturnFalseIsNotMatchesString() {
        StringConditions stringConditions = new StringConditionsImpl("Some MAGICAL text.");

        assertThat(stringConditions.matches("magical")).isFalse();
    }

    @Test
    public void shouldReturnFalseForMatchesStringIfTargetStringIsNull() {
        StringConditions stringConditions = new StringConditionsImpl(null);

        assertThat(stringConditions.matches("magical")).isFalse();
    }

    //matches pattern

    @Test
    public void shouldReturnTrueIfMatchesPattern() {
        StringConditions stringConditions = new StringConditionsImpl("Some magical text.");

        assertThat(stringConditions.matches(Pattern.compile("^Some .* text.$"))).isTrue();
    }

    @Test
    public void shouldReturnFalseIsNotMatchesPattern() {
        StringConditions stringConditions = new StringConditionsImpl("Some MAGICAL text.");

        assertThat(stringConditions.matches(Pattern.compile("magical"))).isFalse();
    }

    @Test
    public void shouldReturnFalseForMatchesPatternIfTargetStringIsNull() {
        StringConditions stringConditions = new StringConditionsImpl(null);

        assertThat(stringConditions.matches(Pattern.compile("magical"))).isFalse();
    }
}
