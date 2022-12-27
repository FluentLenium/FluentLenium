package io.fluentlenium.core.domain;

import io.fluentlenium.core.components.ComponentInstantiator;import io.fluentlenium.core.inject.NoInject;import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.inject.NoInject;
import org.openqa.selenium.WebElement;

/**
 * A component encapsulate a Selenium WebElement, the FluentLenium control interface and FluentLenium instantiator.
 */
public class Component {
    @NoInject
    protected WebElement webElement;
    protected FluentControl control;
    protected ComponentInstantiator instantiator;

    /**
     * Creates a new component
     *
     * @param webElement   Selenium element
     * @param control      FluentLenium control interface
     * @param instantiator FluentLenium instantiator
     */
    public Component(WebElement webElement, FluentControl control, ComponentInstantiator instantiator) {
        this.webElement = webElement;
        this.control = control;
        this.instantiator = instantiator;
    }
}
