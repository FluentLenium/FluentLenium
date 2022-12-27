package io.fluentlenium.assertj.integration.page;

import io.fluentlenium.assertj.integration.page.pages.IndexPage;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.assertj.integration.IntegrationTest;
import io.fluentlenium.assertj.integration.page.pages.IndexPage;
import io.fluentlenium.core.annotation.Page;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class UrlTest extends IntegrationTest {

    @Page
    private IndexPage indexPage;

    @BeforeMethod
    public void setUp() {
        goTo(DEFAULT_URL);
    }

    @Test
    public void verifyHasUrlNegative() {
        assertThatThrownBy(() -> assertThat(indexPage).hasUrl("https://fluentlenium.io"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("https://fluentlenium.io")
                .hasMessageContaining("Expected");
    }

}
