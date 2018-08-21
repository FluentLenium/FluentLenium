package org.fluentlenium.adapter.cucumber.integration.configuration.hook.page;

import org.fluentlenium.adapter.cucumber.integration.utils.UrlUtil;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.hook.wait.Wait;

import static org.assertj.core.api.Assertions.assertThat;

@Wait
public class LocalWithHookPage2 extends FluentPage {

    @Override
    public String getUrl() {
        return UrlUtil.getAbsoluteUrlFromFile("html/index.html");
    }

    @Override
    public void isAt() {
        assertThat(window().title()).contains("Page 2");
    }
}
