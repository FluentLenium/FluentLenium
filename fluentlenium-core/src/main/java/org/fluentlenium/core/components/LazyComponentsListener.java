package org.fluentlenium.core.components;

import org.openqa.selenium.WebElement;

import java.util.Map;

public interface LazyComponentsListener<T> {
    void lazyComponentsInitialized(Map<WebElement, T> componentMap);
}
