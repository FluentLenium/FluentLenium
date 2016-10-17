package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;

/**
 * Abstract implementation supported negation and instantiation.
 *
 * @param <T> type of condition
 */
public abstract class AbstractObjectConditions<T> implements Conditions<T>, ConditionsObject<T> {
    protected final T object;
    protected boolean negation;

    /**
     * Initialize the conditions with given object.
     *
     * @param object underlying object
     */
    public AbstractObjectConditions(final T object) {
        this.object = object;
    }

    /**
     * Initialize the conditions with given object
     *
     * @param object   underlying object
     * @param negation negation value
     */
    public AbstractObjectConditions(final T object, final boolean negation) {
        this.object = object;
        this.negation = negation;
    }

    @Override
    public boolean verify(final Predicate<T> predicate) {
        boolean predicateResult = predicate.apply(object);
        if (negation) {
            predicateResult = !predicateResult;
        }
        return predicateResult;
    }

    @Override
    public T getActualObject() {
        return object;
    }

    /**
     * Creates a new instance of this condition.
     *
     * @param negationValue negation value
     * @return new instance of this condition
     */
    protected abstract AbstractObjectConditions<T> newInstance(boolean negationValue);

    @Override
    @Negation
    public AbstractObjectConditions<T> not() {
        return newInstance(!negation);
    }
}
