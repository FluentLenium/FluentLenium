package org.fluentlenium.core.filter;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.SearchFilter;

import java.util.Collection;

public class PredicateFilter implements SearchFilter {
    private final Predicate<FluentWebElement> predicate;

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
    public Collection<FluentWebElement> applyFilter(final Collection<FluentWebElement> elements) {
        return Collections2.filter(elements, predicate);
    }
}
