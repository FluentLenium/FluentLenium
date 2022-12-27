package io.fluentlenium.pages;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.test.IntegrationFluentTest;

import static org.assertj.core.api.Assertions.assertThat;

public class Page2 extends FluentPage {

    @Override
    public String getUrl() {
        return IntegrationFluentTest.PAGE_2_URL;
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).isEqualTo("Page 2");
    }

}
