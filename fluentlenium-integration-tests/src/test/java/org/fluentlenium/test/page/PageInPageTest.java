package org.fluentlenium.test.page;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PageInPageTest extends IntegrationFluentTest {

    @Page
    private TestPage testPage;

    @Page
    private SubSubTestPage subTestPage;

    @Test
    void pagesShouldBeInjected() {
        assertThat(testPage).isNotNull();
        assertThat(testPage).isInstanceOf(TestPage.class);
        assertThat(testPage.includedPage).isNotNull();
        assertThat(testPage.includedPage).isInstanceOf(IncludedPage.class);
        assertThat(testPage.includedPage.element).isNotNull();
        assertThat(subTestPage).isNotNull();
        assertThat(subTestPage).isInstanceOf(SubTestPage.class);
        assertThat(subTestPage.includedPage).isNotNull();
        assertThat(subTestPage.anotherIncludedPage).isNotNull();
    }
}

class TestPage extends FluentPage {

    @Page
    /* default */ IncludedPage includedPage;
}

class SubSubTestPage extends SubTestPage {
}

class SubTestPage extends TestPage {

    @Page
    /* default */ IncludedPage anotherIncludedPage;
}

class SubTestPageWithCreate extends FluentPage {

    private IncludedPage pageWithCreatePage;

    public void initFluent(FluentControl control) {
        super.initFluent(control);
        pageWithCreatePage = newInstance(IncludedPage.class);
    }

    IncludedPage getPageWithCreatePage() {
        return pageWithCreatePage;
    }
}

class IncludedPage extends FluentPage {

    /* default */ FluentWebElement element;
}
