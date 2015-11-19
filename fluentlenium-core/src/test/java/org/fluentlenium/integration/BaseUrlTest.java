package org.fluentlenium.integration;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseUrlTest extends LocalFluentCase {
    @Page
    Page2Relative pageRelative;

    @Page
    Page2 page;

    @Override
    public String getDefaultBaseUrl() {
        return DEFAULT_URL;
    }

    @Test
    public void baseUrlShouldBeUsedForRelativeUrlInGoTo() {
        goTo(LocalFluentCase.BASE_URL + "/page2.html");
        assertThat(title()).isEqualTo("Page 2");
    }

    @Test
    public void baseUrlShouldNotBeUsedForAbsoluteUrlInGoTo() {
        goTo(DEFAULT_URL);
        assertThat(title()).isEqualTo("Fluent Selenium Documentation");
    }

    @Test
    public void baseUrlShouldBeUsedForRelativeUrlInPageGo() {
        goTo(pageRelative);
        pageRelative.isAt();
    }

    @Test
    public void baseUrlShouldNotBeUsedForAbsoluteUrlInPageGo() {
        goTo(page);
        page.isAt();
    }

}

class Page2Relative extends FluentPage {

    @Override
    public String getUrl() {
        return LocalFluentCase.BASE_URL + "/page2.html";
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).isEqualTo("Page 2");
    }
}

