package org.fluentlenium.test.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for {@link org.fluentlenium.core.FluentPage}.
 */
class PageUrlParameterQueryTest extends IntegrationFluentTest {

    @Page
    private Page2UrlPage page;

    @Page
    private Page2UrlOptionalPage optionalPage;

    @Test
    void shouldReturnParameterValue() {
        page.go("param1val", "param2val");

        assertThat(page.getParam("param1")).isEqualTo("param1val");
    }

    @Test
    void shouldReturnNullWhenParameterIsNotPresent() {
        page.go("param1val", "param2val");

        assertThat(page.getParam("param4")).isNull();
    }

    @Test
    void shouldReturnOptionalParameterValueWhenPresent() {
        optionalPage.go("param1val", "param2val");

        assertThat(page.getParam("param2")).isEqualTo("param2val");
    }

    @Test
    void shouldReturnNullWhenOptionalParameterValueIsNotPresent() {
        optionalPage.go("param1val");

        assertThat(page.getParam("param2")).isNull();
    }
}

@PageUrl(file = "page2url.html", value = "?param1={param1}&param2={param2}")
class Page2UrlPage extends FluentPage {
}

@PageUrl(file = "page2url.html", value = "?param1={param1}&param2={?param2}")
class Page2UrlOptionalPage extends FluentPage {
}
