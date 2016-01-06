package org.fluentlenium.integration;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Before;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

public class PageTest extends LocalFluentCase {
    @Page
    PageAccueil page;

    @Page
    Page2 page2;

    @Page
    Page3 page3;

    @Test
    public void checkGoTo() {
        page.go();
        assertThat(title()).contains("Selenium");
    }

    @Test
    public void checkIsAt() {
        page.go();
        page.isAt();
    }

    @Test(expected = ComparisonFailure.class)
    public void checkIsAtFailed() {
        page.go();
        page2.isAt();
    }

    @Test
    public void checkFollowLink() {
        page.go();
        page.goToNextPage();
        page2.isAt();
    }

    @Test
    public void checkFollowLinkWithBddStyle() {
        goTo(page);
        assertAt(page);
        page.goToNextPage();
        assertAt(page2);
    }

    @Test
    public void checkFollowLinkFoundWithFindBy() {
        page.go();
        page.goToNextPageWithFindByClassLink();
        page2.isAt();
    }

    // Recursive instantiation for @Page fields in FluentPage::createPage #168
    @Test
    public void checkFieldsInitialized() {
        page3.go();
        assertThat(page3.linkToPage2FoundWithFindBy).isNotNull();
        assertThat(page3.linkToPage2FoundWithFindByOnPage3).isNotNull();
    }

    @Test
    public void checkManuallyCreatedSupportInjection() {
        Page4 page4 = createPage(Page4.class);
        assertThat(page4.getPageAccueil()).isNotNull();
        assertThat(page4.getPage5()).isNotNull();
        assertThat(page4.getPage5().getPageAccueil()).isNotNull();
    }
}

class PageAccueil extends FluentPage {

    FluentWebElement linkToPage2;

    @FindBy(css = "a.go-next")
    FluentWebElement linkToPage2FoundWithFindBy;

    @Override
    public String getUrl() {
        return LocalFluentCase.DEFAULT_URL;
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
        return LocalFluentCase.PAGE_2_URL;
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).isEqualTo("Page 2");
    }

}

class Page3 extends PageAccueil {
    @FindBy(css = "a.go-next")
    FluentWebElement linkToPage2FoundWithFindByOnPage3;
}

class Page4 extends FluentPage {
    @Page
    private PageAccueil pageAccueil;

    private Page5 page5;

    public Page4() {
        page5 = createPage(Page5.class);
    }

    public PageAccueil getPageAccueil() {
        return pageAccueil;
    }

    public Page5 getPage5() {
        return page5;
    }
}

class Page5 extends FluentPage {
    @Page
    private PageAccueil pageAccueil;

    public PageAccueil getPageAccueil() {
        return pageAccueil;
    }
}
