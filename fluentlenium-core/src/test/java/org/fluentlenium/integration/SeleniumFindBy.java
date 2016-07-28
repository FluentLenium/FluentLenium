package org.fluentlenium.integration;

import org.assertj.core.api.Assertions;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Check that @FindBy and @FindAll works with default Selenium elements.
 */
public class SeleniumFindBy extends LocalFluentCase {
    @Page
    PageIndex page;

    public void should_findBy_retrieve_element() {
        page.go();
        page.isAt();

        Assertions.assertThat(page.location.getText()).isEqualTo("Pharmacy");
    }

    @Test
    public void should_findBy_retrieve_list() {
        page.go();
        page.isAt();
        Assertions.assertThat(page.smalls).hasSize(3);

        ArrayList<String> texts = new ArrayList<>();
        for (WebElement e : page.smalls) {
            texts.add(e.getText());
        }

        Assertions.assertThat(texts).containsExactly("Small 1", "Small 2", "Small 3");
    }

    @Test
    public void should_findAll_findBy_retrieve_list() {
        page.go();
        page.isAt();
        Assertions.assertThat(page.findAllElements).hasSize(4);

        ArrayList<String> texts = new ArrayList<>();
        for (WebElement e : page.findAllElements) {
            texts.add(e.getText());
        }

        Assertions.assertThat(texts).containsExactly("Pharmacy", "Small 1", "Small 2", "Small 3");
    }

    @Test
    public void should_findBy_retrieved_object_work_for_selenium_actions() {
        page.go();
        page.isAt();
        assertThat(page.getText()).isEqualTo("This text should change on MouseOver");
        page.hoverOverElement();
        assertThat(page.getText()).isEqualTo("abc");
    }

    private static class PageIndex extends FluentPage {
        @FindBy(id = "location")
        WebElement location;

        @FindBy(className = "small")
        List<WebElement> smalls;

        @FindAll({ @FindBy(id = "location"), @FindBy(className = "small") })
        List<WebElement> findAllElements;

        @FindBy(css = "#mouseover")
        private FluentWebElement mouseOverElement;

        @FindBy(css = "#id3")
        private FluentWebElement id3;

        @Override
        public String getUrl() {
            return LocalFluentCase.DEFAULT_URL;
        }

        @Override
        public void isAt() {
            assertThat(getDriver().getTitle()).contains("Selenium");
        }

        public void hoverOverElement() {
            mouseOverElement.mouse().moveToElement();
        }

        public String getText() {
            return id3.getText();
        }
    }
}



