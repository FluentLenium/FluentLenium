package org.fluentlenium.adapter.spock.page

import org.fluentlenium.adapter.spock.utils.UrlUtil
import org.fluentlenium.core.FluentPage

class Page2 extends FluentPage {
    @Override
    String getUrl() {
        return UrlUtil.getAbsoluteUrlFromFile("page2.html")
    }
}
