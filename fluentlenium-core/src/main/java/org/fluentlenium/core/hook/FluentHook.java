package org.fluentlenium.core.hook;

import com.google.common.base.Supplier;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public interface FluentHook<T> extends WebElement, ElementLocator {
    T getOptions();
}
