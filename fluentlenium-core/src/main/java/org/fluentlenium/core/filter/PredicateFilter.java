package org.fluentlenium.core.filter;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.SearchFilter;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Search filter based on a predicate.
 */
public class PredicateFilter implements SearchFilter {
    private final Predicate<FluentWebElement> predicate;

    /**
     * Creates a new predicate search filter.
     *
     * @param predicate search filter
     */
    PredicateFilter(Predicate<FluentWebElement> predicate) {
        this.predicate = predicate;
    }

    @Override
    public String getCssFilter() {
        return null;
    }

    @Override
    public boolean isCssFilterSupported() {
        return false;
    }

    @Override
    public Collection<FluentWebElement> applyFilter(Collection<FluentWebElement> elements) {
        return elements.stream().filter(predicate).collect(Collectors.toList());
    }
}
