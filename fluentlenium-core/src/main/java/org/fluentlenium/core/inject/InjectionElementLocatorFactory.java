package org.fluentlenium.core.inject;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

public class InjectionElementLocatorFactory implements ElementLocatorFactory {
    private final SearchContext searchContext;

    public InjectionElementLocatorFactory(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    public InjectionElementLocator createLocator(Field field) {
        return new InjectionElementLocator(searchContext, new InjectionAnnotations(field), !Iterable.class.isAssignableFrom(field.getType()));
    }
}
