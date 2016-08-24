package org.fluentlenium.adapter.cucumber.integration.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.adapter.cucumber.integration.utils.UrlUtil;

public class LocalPage extends FluentPage {

    @Override
    public String getUrl() {
        return UrlUtil.getAbsoluteUrlFromFile("html/index.html");
    }
}
