package org.fluentlenium.core.domain;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.openqa.selenium.WebElement;

public class Component {
    protected WebElement webElement;
    protected FluentControl fluentControl;
    protected ComponentInstantiator instantiator;

    public Component(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
        this.webElement = webElement;
        this.fluentControl = fluentControl;
        this.instantiator = instantiator;
    }
}
