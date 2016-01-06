package org.fluentlenium.cucumber.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.integration.util.UrlUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalPage2 extends FluentPage {

    @Override
    public String getUrl() {
        return UrlUtil.getAbsoluteUrlFromFile("html/index.html");
    }

    @Override
    public void isAt() {
        assertThat(title()).contains("Page 2");
    }
}
