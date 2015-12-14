package org.fluentlenium.cucumber.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.integration.util.UrlUtil;

public class LocalPage extends FluentPage {

    @Override
    public String getUrl() {
        return UrlUtil.getAbsoluteUrlFromFile("html/index.html");
    }
}
