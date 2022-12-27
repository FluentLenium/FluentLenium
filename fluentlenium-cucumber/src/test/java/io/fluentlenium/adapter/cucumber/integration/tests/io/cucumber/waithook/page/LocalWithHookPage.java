package io.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.waithook.page;

import io.fluentlenium.core.FluentPage;import io.fluentlenium.core.domain.FluentWebElement;import io.fluentlenium.core.hook.wait.Wait;import io.fluentlenium.utils.UrlUtils;import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.hook.wait.Wait;
import org.openqa.selenium.support.FindBy;

import static io.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

@Wait
public class LocalWithHookPage extends FluentPage {

    @FindBy(css = "a#linkToPage2")
    private FluentWebElement link;

    @Override
    public String getUrl() {
        return UrlUtils.getAbsoluteUrlFromFile("html/index.html");
    }

    @Override
    public void isAtUsingUrl(String urlTemplate) {
        // Skip because it doesn't work with file:// urls ...
    }

    public void clickLink() {
        link.click();
    }
}
