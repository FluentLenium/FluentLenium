package io.fluentlenium.core.action;

import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;

/**
 * Default form filling features.
 * <p>
 * Documentation can also be found at the FluentLenium website at
 * <a href="https://fluentlenium.io/docs/locators/#filling-forms">Locators / Filling Forms</a>.
 *
 * @param <E> type of elements to fill
 */
public class Fill<E extends FluentWebElement> extends BaseFill<E> {
    /**
     * Creates a new fill from a list of elements.
     *
     * @param list list of elements to fill
     */
    public Fill(FluentList<E> list) {
        super(list);
    }

    /**
     * Creates a new fill from a single element.
     *
     * @param element element to fill
     */
    public Fill(E element) {
        super(element);
    }

    /**
     * Fills the underlying elements with the provided texts.
     *
     * @param textValues value to search
     * @return this Fill instance
     * @see FluentList#write(String...)
     * @see FluentWebElement#write(String...)
     */
    public Fill with(String... textValues) {
        getElements().write(textValues);
        return this;
    }

    /**
     * Fills the underlying elements with the provided texts.
     * <p>
     * Synonym for {@link #with(String...)}.
     *
     * @param textValues value to search
     * @return this Fill instance
     */
    public Fill withText(String... textValues) {
        return with(textValues);
    }
}
