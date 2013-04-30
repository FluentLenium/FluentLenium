package org.fluentlenium.integration;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 *
 */
public class PageInPageWithCreatePageTest extends LocalFluentCase {

    @Page
    private SubTestPage subTestPage;

    @Test
    public void pages_should_be_injected() {
        TestPage testPage = createPage(TestPage.class);
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
