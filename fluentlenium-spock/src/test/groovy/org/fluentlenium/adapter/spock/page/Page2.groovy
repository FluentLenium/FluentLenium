package org.fluentlenium.adapter.spock.page

import org.fluentlenium.core.FluentPage
import org.fluentlenium.utils.UrlUtils

class Page2 extends FluentPage {
    @Override
    String getUrl() {
        return UrlUtils.getAbsoluteUrlFromFile("page2.html")
    }
}
