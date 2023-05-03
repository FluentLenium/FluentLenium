package io.fluentlenium.assertj.integration.page;

import io.fluentlenium.assertj.integration.IntegrationTest;
import io.fluentlenium.assertj.integration.page.pages.IndexPage;
import io.fluentlenium.core.annotation.Page;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TitleTest extends IntegrationTest {

    @Page
    private IndexPage indexPage;

    @BeforeMethod
    public void setUp() {
        goTo(DEFAULT_URL);
    }

    @Test
    public void verifyHasTitle() {
        assertThat(indexPage).hasTitle("Fluent Selenium Documentation");
    }

    @Test
    public void verifyHasTitleNegative() {
        assertThatThrownBy(() -> assertThat(indexPage).hasTitle("Wrong title"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Current page title is Fluent Selenium Documentation."
                        + " Expected Wrong title");
    }

}
