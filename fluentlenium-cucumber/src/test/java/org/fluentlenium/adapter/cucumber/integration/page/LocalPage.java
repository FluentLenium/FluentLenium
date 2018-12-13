package org.fluentlenium.adapter.cucumber.integration.page;

import org.fluentlenium.adapter.cucumber.integration.utils.UrlUtil;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class LocalPage extends FluentPage {

    @FindBy(css = "a#linkToPage2")
    private FluentWebElement link;

    @Override
    public String getUrl() {
        return UrlUtil.getAbsoluteUrlFromFile("html/index.html");
    }

    public void clickLink() {
        link.click();
    }

    public void clickLinkWithSearch() {
        FluentWebElement element = el("a#linkToPage2");
        element.click();
    }

    @Override
    protected void isAtUsingUrl(String urlTemplate) {
        // Skip because it doesn't work with file:// urls ...
    }
}
