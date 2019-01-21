package org.fluentlenium.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.opentest4j.AssertionFailedError;

class PageTest extends IntegrationFluentTest {

    private static final String FIND_BY_ELEMENT_NOT_FOUND_FOR_PAGE = "@FindBy element not found for page";
    private static final String BY_ID = "By.id";
    private static final String BY_CHAINED = "By.chained";
    private static final String BY_ALL = "By.all";
    @Page
    /* default */ IndexPage page;

    @Page
    private Page2 page2;

    @Page
    private Page3 page3;

    @Page
    IndexPage2 page4;

    @Page
    IndexPageWithFindByAnnotation indexPageWithFindByAnnotation;

    @Page
    IndexPageWithFindBysAnnotation indexPageWithFindBysAnnotation;

    @Page
    IndexPageWithFindAllAnnotation indexPageWithFindAllAnnotation;

    @Page
    FailingIndexPageWithFindByAnnotation failingIndexPageWithFindByAnnotation;

    @Page
    FailingIndexPageWithFindBysAnnotation failingIndexPageWithFindBysAnnotation;

    @Page
    FailingIndexPageWithFindAllAnnotation failingIndexPageWithFindAllAnnotation;

    @Test
    void checkGoTo() {
        page.go();
        assertThat(window().title()).contains("Selenium");
    }

    @Test
    void checkIsAt() {
        page.go();
        page.isAt();
    }

    @Test
    void checkIsAtFailed() {
        assertThrows(AssertionFailedError.class,
                () -> {
                    page.go();
                    page2.isAt();
                });
    }

    @Test
    void checkFollowLink() {
        page.<IndexPage>go().goToNextPage();
        page2.isAt();
    }

    @Test
    void checkFollowLink2() {
        page4.go().goToNextPage();
        page2.isAt();
    }

    @Test
    void checkFollowLinkWithBddStyle() {
        goTo(page);
        page.isAt();
        page.goToNextPage();
        page2.isAt();
    }

    @Test
    void checkFollowLinkFoundWithFindBy() {
        page.<IndexPage>go().goToNextPageWithFindByClassLink();
        page2.isAt();
    }

    // Recursive instantiation for @Page fields in FluentPage::createPage #168
    @Test
    void checkFieldsInitialized() {
        page3.go();
        assertThat(page3.linkToPage2FoundWithFindBy).isNotNull();
        assertThat(page3.linkToPage2FoundWithFindByOnPage3).isNotNull();
    }

    @Test
    void checkManuallyCreatedSupportInjection() {
        Page4 page4 = newInstance(Page4.class);
        assertThat(page4.getIndexPage()).isNotNull();
        assertThat(page4.getPage5()).isNotNull();
        assertThat(page4.getPage5().getIndexPage()).isNotNull();
    }

    @Test
    void checkPageIsAtWithFindByAnnotation() {
        indexPageWithFindByAnnotation.go().isAt();
    }

    @Test
    void checkPageIsAtWithFindBysAnnotation() {
        goTo(indexPageWithFindBysAnnotation).isAt();
    }

    @Test
    void checkPageIsAtWithFindAllAnnotation() {
        goTo(indexPageWithFindAllAnnotation).isAt();
    }

    @Test
    void checkPageIsAtWithFindByAnnotationShouldFail() {
        AssertionError assertionError = null;
        failingIndexPageWithFindByAnnotation.go();

        try {
            failingIndexPageWithFindByAnnotation.isAt();
        } catch (AssertionError error) {
            assertionError = error;
        }

        assertThat(assertionError.getMessage()).contains(FIND_BY_ELEMENT_NOT_FOUND_FOR_PAGE);
        assertThat(assertionError.getCause().getMessage()).contains(BY_ID);
    }

    @Test
    void checkPageIsAtWithFindBysAnnotationShouldFail() {
        AssertionError assertionError = null;

        try {
            goTo(failingIndexPageWithFindBysAnnotation).isAt();
        } catch (AssertionError error) {
            assertionError = error;
        }

        assertThat(assertionError.getMessage()).contains(FIND_BY_ELEMENT_NOT_FOUND_FOR_PAGE);
        assertThat(assertionError.getCause().getMessage()).contains(BY_CHAINED);

    }

    @Test
    void checkPageIsAtWithFindAllAnnotationShouldFail() {
        AssertionError assertionError = null;

        try {
            goTo(failingIndexPageWithFindAllAnnotation).isAt();
        } catch (AssertionError error) {
            assertionError = error;
        }

        assertThat(assertionError.getMessage()).contains(FIND_BY_ELEMENT_NOT_FOUND_FOR_PAGE);
        assertThat(assertionError.getCause().getMessage()).contains(BY_ALL);
    }
}

class IndexPage extends FluentPage {

    private FluentWebElement linkToPage2;

    @FindBy(css = "a.go-next")
    /* default */ FluentWebElement linkToPage2FoundWithFindBy;

    @Override
    public String getUrl() {
        return IntegrationFluentTest.DEFAULT_URL;
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).contains("Selenium");
    }

    void goToNextPage() {
        linkToPage2.click();
    }

    void goToNextPageWithFindByClassLink() {
        linkToPage2FoundWithFindBy.click();
    }
}

class IndexPage2 extends IndexPage {
    @Override
    public IndexPage2 go() {
        return super.go();
    }
}

@FindBys({@FindBy(className = "parent"), @FindBy(className = "child")})
class IndexPageWithFindBysAnnotation extends FluentPage {
    @Override
    public String getUrl() {
        return IntegrationFluentTest.DEFAULT_URL;
    }
}

@FindBy(id = "oneline")
class IndexPageWithFindByAnnotation extends FluentPage {
    @Override
    public String getUrl() {
        return IntegrationFluentTest.DEFAULT_URL;
    }
}

@FindAll({@FindBy(id = "oneline"), @FindBy(className = "small")})
class IndexPageWithFindAllAnnotation extends FluentPage {
    @Override
    public String getUrl() {
        return IntegrationFluentTest.DEFAULT_URL;
    }
}

@FindBys({@FindBy(className = "notparent"), @FindBy(className = "child")})
class FailingIndexPageWithFindBysAnnotation extends FluentPage {
    @Override
    public String getUrl() {
        return IntegrationFluentTest.DEFAULT_URL;
    }
}

@FindBy(id = "notoneline")
class FailingIndexPageWithFindByAnnotation extends FluentPage {
    @Override
    public String getUrl() {
        return IntegrationFluentTest.DEFAULT_URL;
    }
}

@FindAll({@FindBy(id = "notoneline"), @FindBy(className = "notsmall")})
class FailingIndexPageWithFindAllAnnotation extends FluentPage {
    @Override
    public String getUrl() {
        return IntegrationFluentTest.DEFAULT_URL;
    }
}

class Page2 extends FluentPage {

    @Override
    public String getUrl() {
        return IntegrationFluentTest.PAGE_2_URL;
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).isEqualTo("Page 2");
    }

}

class Page3 extends IndexPage {

    @FindBy(css = "a.go-next")
    /* default */ FluentWebElement linkToPage2FoundWithFindByOnPage3;
}

class Page4 extends FluentPage {

    @Page
    private IndexPage indexPage;

    private Page5 page5;

    @Override
    public void initFluent(FluentControl control) {
        super.initFluent(control);
        page5 = newInstance(Page5.class);
    }

    IndexPage getIndexPage() {
        return indexPage;
    }

    Page5 getPage5() {
        return page5;
    }
}

class Page5 extends FluentPage {

    @Page
    private IndexPage indexPage;

    IndexPage getIndexPage() {
        return indexPage;
    }
}
