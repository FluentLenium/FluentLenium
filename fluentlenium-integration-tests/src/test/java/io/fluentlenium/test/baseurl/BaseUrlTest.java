package io.fluentlenium.test.baseurl;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.annotation.PageUrl;
import io.fluentlenium.pages.Page2;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseUrlTest extends IntegrationFluentTest {

    @Page
    private Page2Relative pageRelative;

    @Page
    private Page2 page;

    @Override
    public String getBaseUrl() {
        return DEFAULT_URL_PATH;
    }

    @Test
    void baseUrlShouldBeUsedForRelativeUrlInGoTo() {
        goTo(PAGE_2_URL);
        assertThat(window().title()).isEqualTo("Page 2");
    }

    @Test
    void baseUrlShouldNotBeUsedForAbsoluteUrlInGoTo() {
        goTo(DEFAULT_URL);
        assertThat(window().title()).isEqualTo("Fluent Selenium Documentation");
    }

    @Test
    void baseUrlShouldBeUsedForRelativeUrlInPageGo() {
        goTo(pageRelative);
        pageRelative.isAt();
    }

    @Test
    void baseUrlShouldNotBeUsedForAbsoluteUrlInPageGo() {
        goTo(page);
        page.isAt();
    }

}

@PageUrl("/page2.html")
class Page2Relative extends FluentPage {
    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).isEqualTo("Page 2");
    }
}

