package io.fluentlenium.pages;

import static org.assertj.core.api.Assertions.assertThat;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.test.IntegrationFluentTest;

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
