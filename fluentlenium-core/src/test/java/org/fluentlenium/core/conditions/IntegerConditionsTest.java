package org.fluentlenium.core.conditions;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegerConditionsTest {

    private void numberImpl(int value) {
        IntegerConditions condition = new IntegerConditionsImpl(value);

        assertConditions(condition, value);
        assertNotConditions(condition.not(), value);
    }

    static void assertConditions(IntegerConditions conditions, int value) { // NOPMD CommentDefaultAccessModifier
        assertThat(conditions.verify(input -> input == value)).isTrue();

        assertThat(conditions.equalTo(value - 1)).isFalse();
        assertThat(conditions.equalTo(value)).isTrue();
        assertThat(conditions.equalTo(value + 1)).isFalse();

        assertThat(conditions.greaterThanOrEqualTo(value - 1)).isTrue();
        assertThat(conditions.greaterThanOrEqualTo(value)).isTrue();
        assertThat(conditions.greaterThanOrEqualTo(value + 1)).isFalse();

        assertThat(conditions.greaterThan(value - 1)).isTrue();
        assertThat(conditions.greaterThan(value)).isFalse();
        assertThat(conditions.greaterThan(value + 1)).isFalse();

        assertThat(conditions.lessThanOrEqualTo(value - 1)).isFalse();
        assertThat(conditions.lessThanOrEqualTo(value)).isTrue();
        assertThat(conditions.lessThanOrEqualTo(value + 1)).isTrue();

        assertThat(conditions.lessThan(value - 1)).isFalse();
        assertThat(conditions.lessThan(value)).isFalse();
        assertThat(conditions.lessThan(value + 1)).isTrue();
    }

    static void assertNotConditions(IntegerConditions conditions, int value) { // NOPMD CommentDefaultAccessModifier
        assertThat(conditions.verify(input -> input == value)).isFalse();

        assertThat(conditions.equalTo(value - 1)).isTrue();
        assertThat(conditions.equalTo(value)).isFalse();
        assertThat(conditions.equalTo(value + 1)).isTrue();

        assertThat(conditions.greaterThanOrEqualTo(value - 1)).isFalse();
        assertThat(conditions.greaterThanOrEqualTo(value)).isFalse();
        assertThat(conditions.greaterThanOrEqualTo(value + 1)).isTrue();

        assertThat(conditions.greaterThan(value - 1)).isFalse();
        assertThat(conditions.greaterThan(value)).isTrue();
        assertThat(conditions.greaterThan(value + 1)).isTrue();

        assertThat(conditions.lessThanOrEqualTo(value - 1)).isTrue();
        assertThat(conditions.lessThanOrEqualTo(value)).isFalse();
        assertThat(conditions.lessThanOrEqualTo(value + 1)).isFalse();

        assertThat(conditions.lessThan(value - 1)).isTrue();
        assertThat(conditions.lessThan(value)).isTrue();
        assertThat(conditions.lessThan(value + 1)).isFalse();
    }

    @Test
    public void one() {
        numberImpl(1);
    }

    @Test
    public void three() {
        numberImpl(3);
    }

    @Test
    public void hundred() {
        numberImpl(100);
    }

}
