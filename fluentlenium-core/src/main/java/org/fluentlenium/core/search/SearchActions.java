package org.fluentlenium.core.search;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.openqa.selenium.By;

public interface SearchActions<E extends FluentWebElement> {
    FluentList<E> find(String name, Filter... filters);

    FluentList<E> find(Filter... filters);

    FluentList<E> find(By locator, final Filter... filters);

    E find(String name, Integer number, Filter... filters);

    E find(Integer number, Filter... filters);

    E findFirst(String name, Filter... filters);

    E findFirst(Filter... filters);

    E findFirst(By locator, final Filter... filters);

    E find(By locator, Integer number, final Filter... filters);
}
