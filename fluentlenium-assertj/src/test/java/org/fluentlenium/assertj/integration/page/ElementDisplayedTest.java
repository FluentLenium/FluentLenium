package org.fluentlenium.assertj.integration.page;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.fluentlenium.assertj.integration.page.pages.IndexPage;
import org.fluentlenium.core.annotation.Page;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class ElementDisplayedTest extends IntegrationTest {

    @Page
    private IndexPage indexPage;

    @BeforeMethod
    public void setUp() {
        goTo(DEFAULT_URL);
    }

    @Test
    public void verifyElementDisplayed() {
        assertThat(indexPage).hasElementDisplayed(el("#oneline"));
    }

    @Test
    public void verifyElementDisplayedOnPage() {
        indexPage.verifyElementDisplayed();
    }

    @Test
    public void verifyElementDisplayedNegative() {
        executeScript("document.getElementById(\"disabled\").style.display=\"none\";");
        assertThatThrownBy(() -> assertThat(indexPage).hasElementDisplayed(el("#disabled")))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Element By.cssSelector: #disabled (first)");
    }

}
