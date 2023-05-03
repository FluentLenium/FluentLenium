package io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.waithook.page;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.hook.wait.Wait;
import io.fluentlenium.utils.UrlUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Wait
public class LocalWithHookPage2 extends FluentPage {

    @Override
    public String getUrl() {
        return UrlUtils.getAbsoluteUrlFromFile("html/index.html");
    }

    @Override
    public void isAt() {
        assertThat(window().title()).contains("Page 2");
    }
}
