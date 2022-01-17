package org.fluentlenium.pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Unshadow;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;

public class ShadowRootPage extends FluentPage {
    @Override
    public void verifyIsLoaded() {
        await().until(el("div#container")).displayed();
    }

    @Unshadow(css = {"#container"})
    FluentWebElement inside;

    public String getShadowRootItemText() {
        return inside.text();
    }
}

