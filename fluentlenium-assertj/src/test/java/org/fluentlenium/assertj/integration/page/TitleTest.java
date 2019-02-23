package org.fluentlenium.assertj.integration.page;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.fluentlenium.assertj.integration.page.pages.IndexPage;
import org.fluentlenium.core.annotation.Page;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

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
