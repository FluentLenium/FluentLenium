package io.fluentlenium.pages;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.annotation.Unshadow;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.annotation.Unshadow;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.domain.FluentWebElement;
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

