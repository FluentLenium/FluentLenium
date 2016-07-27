package org.fluentlenium.integration.initialization;

import org.fluentlenium.core.FluentPage;

public class TestAboutBlankPage extends FluentPage {


    @Override
    public String getUrl() {
        return "about:blank";
    }
}
