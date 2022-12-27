package io.fluentlenium.adapter.spock.page


import io.fluentlenium.core.FluentPage
import io.fluentlenium.utils.UrlUtils

class Page2 extends io.fluentlenium.core.FluentPage {
    @Override
    String getUrl() {
        return io.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile("page2.html")
    }
}
