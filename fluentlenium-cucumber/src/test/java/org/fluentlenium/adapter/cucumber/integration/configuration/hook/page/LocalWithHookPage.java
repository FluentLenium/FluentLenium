package org.fluentlenium.adapter.cucumber.integration.configuration.hook.page;

import org.fluentlenium.adapter.cucumber.integration.utils.UrlUtil;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.hook.wait.Wait;

@Wait
public class LocalWithHookPage extends FluentPage {

    @Override
    public String getUrl() {
        return UrlUtil.getAbsoluteUrlFromFile("html/index.html");
    }

    @Override
    protected void isAtUsingUrl(String urlTemplate) {
        // Skip because it doesn't work with file:// urls ...
    }

    public void clickLink() {
        $("a#linkToPage2").click();
    }
}
