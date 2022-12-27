package io.fluentlenium.pages;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Unshadow;
import io.fluentlenium.core.domain.FluentWebElement;

public class ShadowRootPage extends FluentPage {
    @Override
    public void verifyIsLoaded() {
        await().until(el("div#container")).displayed();
    }

    @Unshadow(css = {"#container", "#inside"})
    FluentWebElement inside;

    public String getShadowRootItemText() {
        return inside.text();
    }
}

