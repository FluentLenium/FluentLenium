package org.fluentlenium.core.inject;

import com.google.common.base.Predicate;
import org.fluentlenium.core.annotation.AjaxElement;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ConfigurableAjaxElementLocatorFactory implements ElementLocatorFactory {
    private final SearchContext searchContext;
    private final int timeOutInSeconds;
    private final int pollingEvery;
    private final Predicate<WebElement> usablePredicate;

    private static Predicate<WebElement> newPredicate(Class<? extends Predicate<WebElement>> predicateClass) {
        try {
            return ReflectionUtils.newInstance(predicateClass);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public ConfigurableAjaxElementLocatorFactory(SearchContext searchContext, AjaxElement elem) {
        this(searchContext, elem.timeOutInSeconds(), elem.pollingInterval(), newPredicate(elem.usableCondition()));
    }

    public ConfigurableAjaxElementLocatorFactory(SearchContext searchContext, int timeOutInSeconds, int pollingEvery, Predicate<WebElement> usablePredicate) {
        this.searchContext = searchContext;
        this.timeOutInSeconds = timeOutInSeconds;
        this.pollingEvery = pollingEvery;
        this.usablePredicate = usablePredicate;
    }


    public ElementLocator createLocator(Field field) {
        return new ConfigurableAjaxElementLocator(searchContext, field, timeOutInSeconds, pollingEvery, usablePredicate);
    }
}
