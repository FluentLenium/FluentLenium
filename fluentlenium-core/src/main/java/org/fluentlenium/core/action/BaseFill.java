package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Common form filling features.
 *
 * @param <E> type of element to fill
 */
public class BaseFill<E extends FluentWebElement> {
    private final FluentList<E> fluentList;

    /**
     * Creates a new fill, from a list of element.
     *
     * @param list list of element to fill
     */
    public BaseFill(final FluentList<E> list) {
        this.fluentList = list;
    }

    /**
     * Creates a new fill, from a single element.
     *
     * @param element element to fill
     */
    public BaseFill(final E element) {
        this((FluentList<E>) element.asList());
    }

    /**
     * Get elements to fill
     *
     * @return list of elements
     */
    protected FluentList<E> getElements() {
        return fluentList;
    }
}
