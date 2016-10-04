package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

public class Fill<E extends FluentWebElement> extends AbstractFill<E> {
    public Fill(FluentList<E> list) {
        super(list);
    }

    public Fill(E element) {
        super(element);
    }

    /**
     * Set the values params as text for the fluentList or search a new list with the css selector and filters and add the
     * values param on it
     *
     * @param textValues value to search
     * @return fill constructor
     */
    public Fill with(String... textValues) {
        findElements().write(textValues);
        return this;
    }

    public Fill withText(String... textValues) {
        return with(textValues);
    }
}
