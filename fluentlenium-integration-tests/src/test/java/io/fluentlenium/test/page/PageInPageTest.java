package io.fluentlenium.test.page;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PageInPageTest extends IntegrationFluentTest {

    @Page
    private TestPage testPage;

    @Page
    private SubSubTestPage subTestPage;

    @Test
    void pagesShouldBeInjected() {
        assertThat(testPage).isInstanceOf(TestPage.class);
        assertThat(testPage.includedPage).isInstanceOf(IncludedPage.class);
        assertThat(testPage.includedPage.element).isNotNull();
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
