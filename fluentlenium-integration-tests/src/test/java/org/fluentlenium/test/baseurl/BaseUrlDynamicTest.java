package org.fluentlenium.test.baseurl;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

class BaseUrlDynamicTest extends IntegrationFluentTest {

    @Page
    private Page2Dynamic page;

    @Page
    private Page2DynamicP1 pageP1;

    @Page
    private Page2DynamicP2P1 pageP2P1;

    @Test
    void baseUrlShouldBeUsedForRelativeUrlInGoTo() {
        page.go().isAt();
        checkPageContent("-1", "-1");
    }

    @Test
    void baseUrlShouldBeUsedForRelativeUrlInGoToP1() {
        pageP1.go("ab").isAt();
        checkPageContent("ab", "-1");
    }

    @Test
    void baseUrlShouldBeUsedForRelativeUrlInGoToP2P1() {
        pageP2P1.go("oo1", "abc").isAt();
        checkPageContent("oo1", "abc");
    }

    private void checkPageContent(String param1, String param2) {
        assertThat(window().title()).isEqualTo("Page 2 url");
        assertThat(el("#param1").text()).contains(param1);
        assertThat(el("#param2").text()).contains(param2);
    }
}

@PageUrl("/page2url.html")
class Page2Dynamic extends FluentPage {
    @Override
    public String getUrl() {
        return IntegrationFluentTest.PAGE_2_URL_TEST;
    }
}

@PageUrl("?param1={param1}")
class Page2DynamicP1 extends FluentPage {
    @Override
    public String getUrl() {
        return IntegrationFluentTest.PAGE_2_URL_TEST + super.getUrl();
    }

    @Override
    public void isAtUsingUrl(String urlTemplate) {
        //overridden to skip URL check because PageUrl is not able to get local file path relatively
    }
}

@PageUrl(file = "page2url.html", value = "?param1={param1}&param2={param2}")
class Page2DynamicP2P1 extends FluentPage {
}
