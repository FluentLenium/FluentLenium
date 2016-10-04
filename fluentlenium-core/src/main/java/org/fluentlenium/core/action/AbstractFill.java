package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;

public abstract class AbstractFill<E extends FluentWebElement> {
    private FluentList<E> fluentList;

    public AbstractFill(FluentList<E> list, Filter... filters) {
        this.fluentList = list;
    }

    public AbstractFill(E element) {
        this((FluentList<E>) element.asList());
    }

    protected FluentList<E> findElements() {
        return fluentList;
    }
}
