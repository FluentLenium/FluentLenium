package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;

public abstract class AbstractObjectConditions<T> implements Conditions<T> {
    protected final T object;
    protected boolean negation;

    public AbstractObjectConditions(T object) {
        this.object = object;
    }

    @Override
    public boolean verify(Predicate<T> predicate) {
        boolean predicateResult = predicate.apply(object);
        if (negation) {
            predicateResult = !predicateResult;
        }
        return predicateResult;
    }

    protected abstract AbstractObjectConditions<T> newInstance();

    @Override
    public AbstractObjectConditions<T> not() {
        AbstractObjectConditions<T> negatedConditions = newInstance();
        negatedConditions.negation = !negation;
        return negatedConditions;
    }
}
