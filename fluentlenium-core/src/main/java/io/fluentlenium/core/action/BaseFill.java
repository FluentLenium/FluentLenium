package io.fluentlenium.core.action;

import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;

/**
 * Common class form filling features storing the potential elements to fill.
 *
 * @param <E> type of element to fill
 * @see Fill
 * @see FillSelect
 */
public class BaseFill<E extends FluentWebElement> {
    private final FluentList<E> fluentList;

    /**
     * Creates a new fill, from a list of element.
     *
     * @param list list of element to fill
     */
    public BaseFill(FluentList<E> list) {
        fluentList = list;
    }

    /**
     * Creates a new fill, from a single element.
     *
     * @param element element to fill
     */
    public BaseFill(E element) {
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
