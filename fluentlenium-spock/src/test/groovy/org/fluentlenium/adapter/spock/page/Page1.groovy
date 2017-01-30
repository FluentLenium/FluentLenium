package org.fluentlenium.adapter.spock.page

import org.fluentlenium.adapter.spock.utils.UrlUtil
import org.fluentlenium.core.FluentPage

class Page1 extends FluentPage {
    @Override
    String getUrl() {
        return UrlUtil.getAbsoluteUrlFromFile("page1.html")
    }
}
