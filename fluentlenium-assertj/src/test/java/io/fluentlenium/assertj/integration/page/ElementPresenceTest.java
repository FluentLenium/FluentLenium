package io.fluentlenium.assertj.integration.page;

import io.fluentlenium.assertj.custom.PageAssert;
import io.fluentlenium.assertj.integration.IntegrationTest;
import io.fluentlenium.assertj.integration.page.pages.IndexPage;
import io.fluentlenium.core.annotation.Page;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration test for {@link PageAssert}.
 */
public class ElementPresenceTest extends IntegrationTest {

    @Page
    private IndexPage indexPage;

    @BeforeMethod
    public void setUp() {
        goTo(DEFAULT_URL);
    }

    @Test
    public void verifyElement() {
        assertThat(indexPage).hasElement(el("#oneline"));
        assertThat(indexPage).hasElement(find(".go-next").first());
    }

    @Test
    public void verifyElementOnPage() {
        indexPage.verifyElement();
    }

    @Test
    public void verifyElements() {
        assertThat(indexPage).hasElements($(".small"));
    }

    @Test
    public void verifyElementsOnPage() {
        indexPage.verifyElements();
    }

    @Test
    public void verifyElementNegative() {
        assertThatThrownBy(() -> assertThat(indexPage).hasElement(el("#nonexisting")))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element By.cssSelector: #nonexisting (first) (Lazy Element)"
                        + " is not present on current page");
    }

    @Test
    public void verifyElementsNegative() {
        assertThatThrownBy(() -> assertThat(indexPage).hasElements($("#nonexisting")))
                .isInstanceOf(AssertionError.class)
                .hasMessage("No element selected by 'By.cssSelector: #nonexisting ([])' is present on the page.");
    }

}
