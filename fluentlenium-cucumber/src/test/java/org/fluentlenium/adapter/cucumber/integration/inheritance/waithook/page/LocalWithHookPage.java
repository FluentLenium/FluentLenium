package org.fluentlenium.adapter.cucumber.integration.inheritance.waithook.page;

import org.fluentlenium.adapter.cucumber.integration.utils.UrlUtil;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.hook.wait.Wait;
import org.openqa.selenium.support.FindBy;

@Wait
public class LocalWithHookPage extends FluentPage {

    @FindBy(css = "a#linkToPage2")
    private FluentWebElement link;

    @Override
    public String getUrl() {
        return UrlUtil.getAbsoluteUrlFromFile("html/index.html");
    }

    @Override
    protected void isAtUsingUrl(String urlTemplate) {
        // Skip because it doesn't work with file:// urls ...
    }

    public void clickLink() {
        link.click();
    }
}
