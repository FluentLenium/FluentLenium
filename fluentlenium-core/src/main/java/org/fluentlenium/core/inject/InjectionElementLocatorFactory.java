package org.fluentlenium.core.inject;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

/**
 * {@link org.openqa.selenium.support.pagefactory.ElementLocator} factory used by {@link FluentInjector}.
 */
public class InjectionElementLocatorFactory implements ElementLocatorFactory {
    private final SearchContext searchContext;

    /**
     * Creates a new factory
     *
     * @param searchContext search context
     */
    public InjectionElementLocatorFactory(final SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    @Override
    public InjectionElementLocator createLocator(final Field field) {
        return new InjectionElementLocator(searchContext, new InjectionAnnotations(field),
                !Iterable.class.isAssignableFrom(field.getType()));
    }
}
