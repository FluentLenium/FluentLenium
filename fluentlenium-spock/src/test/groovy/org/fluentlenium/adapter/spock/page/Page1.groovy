package org.fluentlenium.adapter.spock.page

import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentWebElement
import org.fluentlenium.utils.UrlUtils
import org.openqa.selenium.support.FindBy

class Page1 extends FluentPage {

    @FindBy(css = "a#linkToPage2")
    private FluentWebElement link

    @Override
    String getUrl() {
        return UrlUtils.getAbsoluteUrlFromFile("page1.html")
    }

    Page2 clickLink() {
        link.click()
        return newInstance(Page2.class)
    }
}
