package io.fluentlenium.assertj.integration.page;

import io.fluentlenium.assertj.integration.IntegrationTest;
import io.fluentlenium.assertj.integration.page.pages.IndexPage;
import io.fluentlenium.core.annotation.Page;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExpectedUrlTest extends IntegrationTest {

    @Page
    private IndexPage indexPage;

    @BeforeMethod
    public void setUp() {
        goTo(DEFAULT_URL);
    }

    @Test
    public void verifyHasExpectedElementseNegativeAbsent() {
        assertThatThrownBy(() -> assertThat(indexPage).hasExpectedUrl())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Page has not defined @PageUrl");
    }

}
