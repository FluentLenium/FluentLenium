package io.fluentlenium.test.page;

import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PageInPageWithCreatePageTest extends IntegrationFluentTest {

    @Page
    private SubTestPageWithCreate subTestPage;

    @Test
    void pagesShouldBeInjected() {
        assertThat(subTestPage.getPageWithCreatePage()).isInstanceOf(IncludedPage.class);
    }
}
