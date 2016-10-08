package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Default form filling features.
 *
 * @param <E> type of elements to fill
 */
public class Fill<E extends FluentWebElement> extends BaseFill<E> {
    /**
     * Creates a new fill, from a list of element.
     *
     * @param list list of element to fill
     */
    public Fill(final FluentList<E> list) {
        super(list);
    }

    /**
     * Creates a new fill, from a single element.
     *
     * @param element element to fill
     */
    public Fill(final E element) {
        super(element);
    }

    /**
     * Set the values params as text for the fluentList or search a new list with the css selector and filters and add the
     * values param on it
     *
     * @param textValues value to search
     * @return fill constructor
     */
    public Fill with(final String... textValues) {
        getElements().write(textValues);
        return this;
    }

    /**
     * Set the values params as text for the fluentList or search a new list with the css selector and filters and add the
     * values param on it
     *
     * @param textValues value to search
     * @return fill constructor
     */
    public Fill withText(final String... textValues) {
        return with(textValues);
    }
}
