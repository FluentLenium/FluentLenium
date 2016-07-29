package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;

public interface FillControl {

    /**
     * Construct a FillConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @param cssSelector cssSelector
     * @param filters     set of filters in the current context
     * @return fill
     */
    public Fill fill(String cssSelector, Filter... filters);

    /**
     * Construct a FillConstructor with filters in order to allow easy fill.
     * Be careful - only the visible elements are filled
     *
     * @param filters set of filters in the current context
     * @return fill
     */
    public Fill fill(Filter... filters);

    /**
     * Construct a FillConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @param list    Fluent elements list
     * @param filters set of filters in the current context
     * @return fill
     */
    public Fill fill(FluentList<FluentWebElement> list, Filter... filters);

    /**
     * Construct a FillConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @param element Fluent element
     * @param filters set of filters in the current context
     * @return fill
     */
    public Fill fill(FluentWebElement element, Filter... filters);

    /**
     * Construct a FillSelectConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @param cssSelector cssSelector
     * @param filters     set of filters in the current context
     * @return fill select
     */
    FillSelect fillSelect(String cssSelector, Filter... filters);

    /**
     * Construct a FillSelectConstructor with filters in order to allow easy fill.
     * Be careful - only the visible elements are filled
     *
     * @param filters set of filters in the current context
     * @return fill select
     */
    FillSelect fillSelect(Filter... filters);

}
