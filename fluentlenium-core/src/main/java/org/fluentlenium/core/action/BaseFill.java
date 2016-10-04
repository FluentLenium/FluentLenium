package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

public class BaseFill<E extends FluentWebElement> {
    private FluentList<E> fluentList;

    public BaseFill(FluentList<E> list) {
        this.fluentList = list;
    }

    public BaseFill(E element) {
        this((FluentList<E>) element.asList());
    }

    protected FluentList<E> findElements() {
        return fluentList;
    }
}
