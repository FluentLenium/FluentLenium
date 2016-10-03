package org.fluentlenium.core.domain;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.inject.NoInject;
import org.openqa.selenium.WebElement;

public class Component {
    @NoInject
    protected WebElement webElement;
    protected FluentControl control;
    protected ComponentInstantiator instantiator;

    public Component(WebElement webElement, FluentControl control, ComponentInstantiator instantiator) {
        this.webElement = webElement;
        this.control = control;
        this.instantiator = instantiator;
    }
}
