package org.fluentlenium.core.search;

import org.fluentlenium.core.domain.FluentWebElement;

import java.util.Collection;

/**
 * Search filter interface
 */
public interface SearchFilter {
    /**
     * Get the CSS string used by CSS search time filtering.
     *
     * @return css filter string
     */
    String getCssFilter();

    /**
     * Does this filter support filtering at search time with CSS selector.
     *
     * @return true if CSS filtering is supported, else false
     */
    boolean isCssFilterSupported();

    /**
     * Apply the filter.
     *
     * @param elements input elements
     * @return filtered elements
     */
    Collection<FluentWebElement> applyFilter(Collection<FluentWebElement> elements);
}
