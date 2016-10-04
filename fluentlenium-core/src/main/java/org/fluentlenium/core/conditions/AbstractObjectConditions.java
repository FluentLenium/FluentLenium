package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;

public abstract class AbstractObjectConditions<T> implements Conditions<T> {
    protected final T object;
    protected boolean negation;

    public AbstractObjectConditions(final T object) {
        this.object = object;
    }

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

    protected abstract AbstractObjectConditions<T> newInstance(boolean negationValue);

    @Override
    @Negation
    public AbstractObjectConditions<T> not() {
        return newInstance(!negation);
    }
}
