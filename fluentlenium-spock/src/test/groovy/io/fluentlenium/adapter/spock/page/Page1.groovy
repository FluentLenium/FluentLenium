package io.fluentlenium.adapter.spock.page


import io.fluentlenium.core.FluentPage
import io.fluentlenium.core.domain.FluentWebElement
import io.fluentlenium.utils.UrlUtils
import org.openqa.selenium.support.FindBy

class Page1 extends io.fluentlenium.core.FluentPage {

    @FindBy(css = "a#linkToPage2")
    private io.fluentlenium.core.domain.FluentWebElement link

    @Override
    String getUrl() {
        return io.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile("page1.html")
    }

    Page2 clickLink() {
        link.click()
        return newInstance(Page2.class)
    }
}
