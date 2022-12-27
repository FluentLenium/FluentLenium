package io.fluentlenium.adapter.cucumber.integration.page;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.utils.UrlUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalPage2 extends FluentPage {

    @Override
    public String getUrl() {
        return UrlUtils.getAbsoluteUrlFromFile("html/index.html");
    }

    @Override
    public void isAt() {
        assertThat(window().title()).contains("Page 2");
    }
}
