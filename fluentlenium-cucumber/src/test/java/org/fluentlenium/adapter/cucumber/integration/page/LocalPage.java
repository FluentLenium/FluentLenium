package org.fluentlenium.adapter.cucumber.integration.page;

import org.fluentlenium.adapter.cucumber.integration.utils.UrlUtil;
import org.fluentlenium.core.FluentPage;

public class LocalPage extends FluentPage {

    @Override
    public String getUrl() {
        return UrlUtil.getAbsoluteUrlFromFile("html/index.html");
    }

    @Override
    protected void isAtUsingUrl(String urlTemplate) {
        // Skip because it doesn't work with file:// urls ...
    }
}
