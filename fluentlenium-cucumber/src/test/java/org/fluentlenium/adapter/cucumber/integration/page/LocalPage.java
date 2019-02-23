package org.fluentlenium.adapter.cucumber.integration.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

public class LocalPage extends FluentPage {

    @FindBy(css = "a#linkToPage2")
    private FluentWebElement link;

    @Override
    public String getUrl() {
        return getAbsoluteUrlFromFile("html/index.html");
    }

    public void clickLink() {
        link.click();
    }

    public void clickLinkWithSearch() {
        FluentWebElement element = el("a#linkToPage2");
        element.click();
    }

    @Override
    public void isAtUsingUrl(String urlTemplate) {
        // Skip because it doesn't work with file:// urls ...
    }
}
