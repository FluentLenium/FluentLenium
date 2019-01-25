package org.fluentlenium.test.page;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.test.IntegrationFluentTest;
import org.fluentlenium.pages.FailingIndexPageWithFindAllAnnotation;
import org.fluentlenium.pages.FailingIndexPageWithFindByAnnotation;
import org.fluentlenium.pages.FailingIndexPageWithFindBysAnnotation;
import org.fluentlenium.pages.IndexPage;
import org.fluentlenium.pages.IndexPage2;
import org.fluentlenium.pages.IndexPageWithFindAllAnnotation;
import org.fluentlenium.pages.IndexPageWithFindByAnnotation;
import org.fluentlenium.pages.IndexPageWithFindBysAnnotation;
import org.fluentlenium.pages.Page2;
import org.fluentlenium.pages.Page3;
import org.fluentlenium.pages.Page4;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class PageTest extends IntegrationFluentTest {

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
    private IndexPage2 page4;

    @Page
    private IndexPageWithFindByAnnotation indexPageWithFindByAnnotation;

    @Page
    private IndexPageWithFindBysAnnotation indexPageWithFindBysAnnotation;

    @Page
    private IndexPageWithFindAllAnnotation indexPageWithFindAllAnnotation;

    @Page
    private FailingIndexPageWithFindByAnnotation failingIndexPageWithFindByAnnotation;

    @Page
    private FailingIndexPageWithFindBysAnnotation failingIndexPageWithFindBysAnnotation;

    @Page
    private FailingIndexPageWithFindAllAnnotation failingIndexPageWithFindAllAnnotation;

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
        Page4 page = newInstance(Page4.class);
        assertThat(page.getIndexPage()).isNotNull();
        assertThat(page.getPage5()).isNotNull();
        assertThat(page.getPage5().getIndexPage()).isNotNull();
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














