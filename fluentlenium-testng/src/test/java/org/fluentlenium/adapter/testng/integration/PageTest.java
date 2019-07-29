package org.fluentlenium.adapter.testng.integration;

import org.fluentlenium.adapter.testng.integration.localtest.IntegrationFluentTestNg;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;
import org.opentest4j.AssertionFailedError;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PageTest extends IntegrationFluentTestNg {
    @Page
    /* default */ IndexPage page;

    @Page
    /* default */ Page2 page2;

    @BeforeMethod
    public void beforeTest() {
        goTo(page);
    }

    @Test
    public void checkGoTo() {
        assertThat(window().title()).contains("Selenium");
    }

    @Test
    public void checkIsAt() {
        page.isAt();
    }

    @Test(expectedExceptions = AssertionFailedError.class)
    public void checkIsAtFailed() {
        page2.isAt();
    }

    @Test
    public void checkFollowLink() {
        page.goToNextPage();
        page2.isAt();
    }

    @Test
    public void checkFollowLinkWithBddStyle() {
        page.isAt();
        page.goToNextPage();
        page2.isAt();
    }

    @Test
    public void checkFollowLinkFoundWithFindBy() {
        page.go();
        page.goToNextPageWithFindByClassLink();
        page2.isAt();
    }
}

class IndexPage extends FluentPage {

    /* default */ FluentWebElement linkToPage2;

    @FindBy(css = "a.go-next")
    /* default */ FluentWebElement linkToPage2FoundWithFindBy;

    @Override
    public String getUrl() {
        return IntegrationFluentTestNg.DEFAULT_URL;
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).contains("Selenium");
    }

    public void goToNextPage() {
        linkToPage2.click();
    }

    public void goToNextPageWithFindByClassLink() {
        linkToPage2FoundWithFindBy.click();
    }
}

class Page2 extends FluentPage {

    @Override
    public String getUrl() {
        return IntegrationFluentTestNg.DEFAULT_URL + "/page2.html";
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).isEqualTo("Page 2");
    }

}
