package io.fluentlenium.core.conditions;

/**
 * Conditions for integer
 */
public class IntegerConditionsImpl extends AbstractObjectConditions<Integer> implements IntegerConditions {
    /**
     * Creates a new conditions object on integer.
     *
     * @param integer underlying integer
     */
    public IntegerConditionsImpl(Integer integer) {
        super(integer);
    }

    /**
     * Creates a new conditions object on integer.
     *
     * @param integer  underlying integer
     * @param negation negation value
     */
    public IntegerConditionsImpl(Integer integer, boolean negation) {
        super(integer, negation);
    }

    @Override
    protected AbstractObjectConditions<Integer> newInstance(boolean negationValue) {
        return new IntegerConditionsImpl(object, negationValue);
    }

    @Override
    @Negation
    public IntegerConditionsImpl not() {
        return (IntegerConditionsImpl) super.not();
    }

    @Override
    public boolean equalTo(int value) {
        return verify(input -> input.equals(value));
    }

    @Override
    public boolean lessThan(int value) {
        return verify(input -> input < value);
    }

    @Override
    public boolean lessThanOrEqualTo(int value) {
        return verify(input -> input <= value);
    }

    @Override
    public boolean greaterThan(int value) {
        return verify(input -> input > value);
    }

    @Override
    public boolean greaterThanOrEqualTo(int value) {
        return verify(input -> input >= value);
    }
}
