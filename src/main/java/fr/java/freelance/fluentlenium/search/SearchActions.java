package fr.java.freelance.fluentlenium.search;

import fr.java.freelance.fluentlenium.domain.FluentList;
import fr.java.freelance.fluentlenium.domain.FluentWebElement;
import fr.java.freelance.fluentlenium.filter.Filter;


public interface SearchActions {
    FluentList find(String name, Filter... filters);

    FluentWebElement find(String name, Integer number, Filter... filters);

    FluentWebElement findFirst(String name, Filter... filters);
}
