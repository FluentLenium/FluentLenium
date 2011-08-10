package fr.javafreelance.fluentlenium.core.search;

import fr.javafreelance.fluentlenium.core.domain.FluentList;
import fr.javafreelance.fluentlenium.core.domain.FluentWebElement;
import fr.javafreelance.fluentlenium.core.filter.Filter;


public interface SearchActions {
    FluentList find(String name, Filter... filters);

    FluentWebElement find(String name, Integer number, Filter... filters);

    FluentWebElement findFirst(String name, Filter... filters);
}
