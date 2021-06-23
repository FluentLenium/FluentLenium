package org.fluentlenium.test.findby;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Check that @FindBy and @FindAll works with default Selenium elements.
 */
class SeleniumFindBy extends IntegrationFluentTest {
    @Page
    private PageIndex page;

    @Test
    void shouldFindByRetrieveElement() {
        page.go();
        page.isAt();

        assertThat(page.location.getText()).isEqualTo("Pharmacy");
    }

    @Test
    void shouldFindByRetrieveList() {
        page.go();
        page.isAt();
        assertThat(page.smalls).hasSize(3);

        List<String> texts = new ArrayList<>();
        for (WebElement e : page.smalls) {
            texts.add(e.getText());
        }

        assertThat(texts).containsExactly("Small 1", "Small 2", "Small 3");
    }

    @Test
    void shouldFindAllFindByRetrieveList() {
        page.go();
        page.isAt();
        assertThat(page.findAllElements).hasSize(4);

        List<String> texts = new ArrayList<>();
        for (WebElement e : page.findAllElements) {
            texts.add(e.getText());
        }

        assertThat(texts).containsExactly("Pharmacy", "Small 1", "Small 2", "Small 3");
    }

    @Test
    void shouldFindByRetrievedObjectWorkForSeleniumActions() {
        page.go();
        page.isAt();
        assertThat(page.getText()).isEqualTo("This text should change on MouseOver");
        page.hoverOverElement();
        assertThat(page.getText()).isEqualTo("abc");
    }

    private static class PageIndex extends FluentPage {
        @FindBy(id = "location")
        private WebElement location;

        @FindBy(className = "small")
        private List<WebElement> smalls;

        @FindAll({@FindBy(id = "location"), @FindBy(className = "small")})
        private List<WebElement> findAllElements;

        @FindBy(css = "#mouseover")
        private FluentWebElement mouseOverElement;

        @FindBy(css = "#id3")
        private FluentWebElement id3;

        @Override
        public String getUrl() {
            return IntegrationFluentTest.DEFAULT_URL;
        }

        @Override
        public void isAt() {
            assertThat(getDriver().getTitle()).contains("Selenium");
        }

        void hoverOverElement() {
            mouseOverElement.mouse().moveToElement();
        }

        public String getText() {
            return id3.text();
        }
    }
}



