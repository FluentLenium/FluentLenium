package org.fluentlenium.integration;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class PageInPageWithCreatePageTest extends LocalFluentCase {

    @Page
    private SubTestPageWithCreate subTestPage;

    @Test
    public void pages_should_be_injected() {
        assertThat(subTestPage.pageWithCreatePage).isNotNull();
        assertThat(subTestPage.pageWithCreatePage).isInstanceOf(IncludedPage.class);
    }
}
