package io.fluentlenium.adapter.cucumber.integration.page;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.utils.UrlUtils;
import org.openqa.selenium.support.FindBy;

public class LocalPage extends FluentPage {

    @FindBy(css = "a#linkToPage2")
    private FluentWebElement link;

    @Override
    public String getUrl() {
        return UrlUtils.getAbsoluteUrlFromFile("html/index.html");
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
