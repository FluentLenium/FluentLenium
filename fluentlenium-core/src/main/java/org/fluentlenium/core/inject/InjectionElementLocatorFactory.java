package org.fluentlenium.core.inject;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

/**
 * {@link org.openqa.selenium.support.pagefactory.ElementLocator} factory used by {@link FluentInjector}.
 */
public class InjectionElementLocatorFactory implements ElementLocatorFactory {
    private final SearchContext searchContext;
    private final Capabilities capabilities;

    /**
     * Creates a new factory
     *
     * @param searchContext search context
     * @param capabilities device capabilities
     */
    public InjectionElementLocatorFactory(SearchContext searchContext, Capabilities capabilities) {
        this.searchContext = searchContext;
        this.capabilities = capabilities;
    }

    @Override
    public InjectionElementLocator createLocator(Field field) {
        return new InjectionElementLocator(searchContext, new InjectionAnnotations(field, capabilities),
                !Iterable.class.isAssignableFrom(field.getType()));
    }
}
