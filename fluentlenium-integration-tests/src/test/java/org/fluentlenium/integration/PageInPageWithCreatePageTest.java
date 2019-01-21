package org.fluentlenium.integration;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PageInPageWithCreatePageTest extends IntegrationFluentTest {

    @Page
    private SubTestPageWithCreate subTestPage;

    @Test
    void pagesShouldBeInjected() {
        assertThat(subTestPage.getPageWithCreatePage()).isNotNull();
        assertThat(subTestPage.getPageWithCreatePage()).isInstanceOf(IncludedPage.class);
    }
}
