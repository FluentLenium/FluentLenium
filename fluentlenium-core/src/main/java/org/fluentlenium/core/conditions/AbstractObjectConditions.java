package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;

public abstract class AbstractObjectConditions<T> implements Conditions<T> {
    protected final T object;
    protected boolean negation;

    public AbstractObjectConditions(T object) {
        this.object = object;
    }

    public AbstractObjectConditions(T object, boolean negation) {
        this.object = object;
        this.negation = negation;
    }

    @Override
    public boolean verify(Predicate<T> predicate) {
        boolean predicateResult = predicate.apply(object);
        if (negation) {
            predicateResult = !predicateResult;
        }
        return predicateResult;
    }

    protected abstract AbstractObjectConditions<T> newInstance(boolean negationValue);

    @Override
    @Negation
    public AbstractObjectConditions<T> not() {
        return newInstance(!negation);
    }
}
