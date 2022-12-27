package io.fluentlenium.assertj.integration.page;

import io.fluentlenium.assertj.integration.page.pages.IndexPage;import io.fluentlenium.core.annotation.Page;import io.fluentlenium.assertj.integration.IntegrationTest;
import io.fluentlenium.assertj.integration.page.pages.IndexPage;
import io.fluentlenium.core.annotation.Page;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

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
