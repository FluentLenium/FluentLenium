package io.fluentlenium.core.search;

import io.fluentlenium.core.proxy.LocatorProxies;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;
import java.util.function.Supplier;

/**
 * Abstract supplier providing toString() representation from elements proxy and search filters.
 */
public abstract class AbstractSearchSupplier implements Supplier<List<WebElement>> {
    private final List<SearchFilter> searchFilters;
    private final Object proxy;

    /**
     * Creates a new search supplier.
     *
     * @param searchFilters filters to display in toString()
     * @param proxy         proxy to use for toString()
     */
    public AbstractSearchSupplier(List<SearchFilter> searchFilters, Object proxy) {
        this.searchFilters = searchFilters;
        this.proxy = proxy;
    }

    @Override
    public String toString() {
        ElementLocator locator = LocatorProxies.getLocatorHandler(proxy).getLocator();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(locator);

        for (int i = 0; i < searchFilters.size(); i++) {
            if (i > 0) {
                stringBuilder.append(" and");
            }
            stringBuilder.append(' ').append(searchFilters.get(i));
        }

        return stringBuilder.toString();
    }
}
