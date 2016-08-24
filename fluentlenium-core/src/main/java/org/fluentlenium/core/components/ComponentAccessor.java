package org.fluentlenium.core.components;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ComponentAccessor {
    /**
     * Get the related component from the given element.
     *
     * @param element
     *
     * @return
     */
    Object getComponent(WebElement element);

}
