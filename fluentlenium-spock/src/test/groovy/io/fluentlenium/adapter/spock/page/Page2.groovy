package io.fluentlenium.adapter.spock.page

class Page2 extends io.fluentlenium.core.FluentPage {
    @Override
    String getUrl() {
        return io.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile("page2.html")
    }
}
