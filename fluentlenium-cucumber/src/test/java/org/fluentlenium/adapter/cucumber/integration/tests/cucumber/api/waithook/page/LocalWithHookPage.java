package org.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.waithook.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.hook.wait.Wait;
import org.openqa.selenium.support.FindBy;

import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

@Wait
public class LocalWithHookPage extends FluentPage {

    @FindBy(css = "a#linkToPage2")
    private FluentWebElement link;

    @Override
    public String getUrl() {
        return getAbsoluteUrlFromFile("html/index.html");
    }

    @Override
    public void isAtUsingUrl(String urlTemplate) {
        // Skip because it doesn't work with file:// urls ...
    }

    public void clickLink() {
        link.click();
    }
}
