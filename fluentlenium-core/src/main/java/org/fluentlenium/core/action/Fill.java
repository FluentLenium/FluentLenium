package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

public class Fill<E extends FluentWebElement> extends BaseFill<E> {
    public Fill(final FluentList<E> list) {
        super(list);
    }

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
        findElements().write(textValues);
        return this;
    }

    public Fill withText(final String... textValues) {
        return with(textValues);
    }
}
