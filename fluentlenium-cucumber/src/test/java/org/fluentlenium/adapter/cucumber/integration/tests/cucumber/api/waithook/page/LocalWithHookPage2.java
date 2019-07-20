package org.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.waithook.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.hook.wait.Wait;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

@Wait
public class LocalWithHookPage2 extends FluentPage {

    @Override
    public String getUrl() {
        return getAbsoluteUrlFromFile("html/index.html");
    }

    @Override
    public void isAt() {
        assertThat(window().title()).contains("Page 2");
    }
}
