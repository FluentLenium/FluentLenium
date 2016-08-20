package org.fluentlenium.core.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebElement;

/**
 * Encapsulate a component and it's WebElement.
 */
@Getter
@Setter
@AllArgsConstructor
public class ComponentBean {
    public Object component;
    public WebElement element;
}
