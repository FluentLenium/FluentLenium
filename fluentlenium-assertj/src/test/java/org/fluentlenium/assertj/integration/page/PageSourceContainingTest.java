package org.fluentlenium.assertj.integration.page;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.fluentlenium.assertj.integration.page.pages.IndexPage;
import org.fluentlenium.core.annotation.Page;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class PageSourceContainingTest extends IntegrationTest {

    @Page
    private IndexPage indexPage;

    @BeforeMethod
    public void setUp() {
        goTo(DEFAULT_URL);
    }

    @Test
    public void verifyHasTitle() {
        assertThat(indexPage).hasPageSourceContaining("Fluent Selenium Documentation");
    }

    @Test
    public void verifyHasTitleNegative() {
        assertThatThrownBy(() -> assertThat(indexPage).hasPageSourceContaining("Not there"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Current page source does not contain: Not there");
    }

}
