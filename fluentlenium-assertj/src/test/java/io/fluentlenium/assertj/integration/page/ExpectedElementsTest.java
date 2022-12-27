package io.fluentlenium.assertj.integration.page;

import io.fluentlenium.assertj.integration.page.pages.IndexPage;import io.fluentlenium.assertj.integration.page.pages.IndexPageNoClassAnnotations;import io.fluentlenium.assertj.integration.page.pages.IndexPageWrongClassAnnotations;import io.fluentlenium.core.annotation.Page;import io.fluentlenium.assertj.integration.IntegrationTest;
import io.fluentlenium.assertj.integration.page.pages.IndexPage;
import io.fluentlenium.assertj.integration.page.pages.IndexPageNoClassAnnotations;
import io.fluentlenium.assertj.integration.page.pages.IndexPageWrongClassAnnotations;
import io.fluentlenium.core.annotation.Page;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class ExpectedElementsTest extends IntegrationTest {

    @Page
    private IndexPage indexPage;

    @Page
    private IndexPageNoClassAnnotations indexPageNoClassAnnotations;

    @Page
    private IndexPageWrongClassAnnotations indexPageWrongClassAnnotations;

    @BeforeMethod
    public void setUp() {
        goTo(DEFAULT_URL);
    }

    @Test
    public void verifyHasExpectedElements() {
        assertThat(indexPage).hasExpectedElements();
    }

    @Test
    public void verifyHasExpectedElementseNegativeAbsent() {
        assertThatThrownBy(() -> assertThat(indexPageNoClassAnnotations).hasExpectedElements())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Page has not defined @FindBy class level annotation");
    }

    @Test
    public void verifyHasExpectedElementseNegativeWrong() {
        assertThatThrownBy(() -> assertThat(indexPageWrongClassAnnotations).hasExpectedElements())
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("@FindBy element not found for page");
    }

}
