package io.fluentlenium.test.page;

import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PageInPageWithAnnotationPageTest extends IntegrationFluentTest {

    @Page
    private SubSubTestPage subTestPage;

    @Test
    void pagesShouldBeInjected() {
        TestPage testPage = newInstance(SubSubTestPage.class);
        assertThat(testPage).isInstanceOf(TestPage.class);
        assertThat(testPage.includedPage).isInstanceOf(IncludedPage.class);
        assertThat(testPage.includedPage.element).isNotNull();
        assertThat(subTestPage).isInstanceOf(SubTestPage.class);
        assertThat(subTestPage.includedPage).isNotNull();
        assertThat(subTestPage.anotherIncludedPage).isNotNull();
    }
}
