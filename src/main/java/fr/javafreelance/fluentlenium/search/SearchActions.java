package fr.javafreelance.fluentlenium.search;

import fr.javafreelance.fluentlenium.domain.FluentList;
import fr.javafreelance.fluentlenium.domain.FluentWebElement;
import fr.javafreelance.fluentlenium.filter.Filter;


public interface SearchActions {
    FluentList find(String name, Filter... filters);

    FluentWebElement find(String name, Integer number, Filter... filters);

    FluentWebElement findFirst(String name, Filter... filters);
}
