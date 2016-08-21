package org.fluentlenium.core.inject;

import com.google.common.base.Predicate;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AjaxElementLocator;

import java.lang.reflect.Field;

public class ConfigurableAjaxElementLocator extends AjaxElementLocator {
    private final int pollingInterval;
    private final Predicate<WebElement> usablePredicate;

    public ConfigurableAjaxElementLocator(SearchContext searchContext, Field field, int timeOutInSeconds, int pollingInterval, Predicate<WebElement> usablePredicate) {
        super(searchContext, field, timeOutInSeconds);
        this.pollingInterval = pollingInterval;
        this.usablePredicate = usablePredicate;
    }

    protected long sleepFor() {
        return pollingInterval;
    }

    protected boolean isElementUsable(WebElement element) {
        return usablePredicate.apply(element);
    }
}
