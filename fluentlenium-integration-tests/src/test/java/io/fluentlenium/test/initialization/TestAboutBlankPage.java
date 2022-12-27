package io.fluentlenium.test.initialization;

import io.fluentlenium.core.FluentPage;

public class TestAboutBlankPage extends FluentPage {

    @Override
    public String getUrl() {
        return "about:blank";
    }
}
