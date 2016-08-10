package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentWebElement;

/**
 * All actions that can be used on the list or on a web element
 */
public interface FluentActions<T, E extends FluentWebElement> {
    T click();

    T submit();

    T text(String... text);

    /**
     * Construct a FillConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @return fill
     */
    Fill<E> fill();

    /**
     * Construct a FillSelectConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @return fill select
     */
    FillSelect<E> fillSelect();
}
