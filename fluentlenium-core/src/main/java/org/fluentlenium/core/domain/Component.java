package org.fluentlenium.core.domain;

import lombok.experimental.Delegate;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.action.InputControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.inject.NoInject;
import org.openqa.selenium.WebElement;

public class Component {
    @NoInject
    protected WebElement webElement;
    @Delegate(excludes = InputControl.class)
    protected FluentControl fluentControl;
    protected ComponentInstantiator instantiator;

    public Component(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
        this.webElement = webElement;
        this.fluentControl = fluentControl;
        this.instantiator = instantiator;
    }
}
