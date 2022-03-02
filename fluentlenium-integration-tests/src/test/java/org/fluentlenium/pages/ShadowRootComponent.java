package org.fluentlenium.pages;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.annotation.Unshadow;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebElement;

public class ShadowRootComponent extends FluentWebElement {
    public ShadowRootComponent(WebElement element, FluentControl control, ComponentInstantiator instantiator) {
        super(element, control, instantiator);
        await().until(el("div#container")).displayed();
    }

    @Unshadow(css = {"#container", "#inside"})
    FluentWebElement inside;

    public String getShadowRootItemText() {
        return inside.text();
    }
}

