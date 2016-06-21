package org.fluentlenium.integration;

import org.assertj.core.api.Assertions;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FindByOfListTest extends LocalFluentCase {

    @Page
    private PageIndex page;

    @Test
    public void should_findBy_retrieve_list() {
        page.go();
        page.isAt();
        Assertions.assertThat(page.smalls).hasSize(3);
        Assertions.assertThat(page.smalls.getTexts()).containsExactly("Small 1", "Small 2", "Small 3");
    }

    @Test
    public void should_findAll_findBy_retrieve_list() {
        page.go();
        page.isAt();
        Assertions.assertThat(page.findAllElements).hasSize(4);
        Assertions.assertThat(page.findAllElements.getTexts()).containsExactly("Pharmacy", "Small 1", "Small 2", "Small 3");
    }

    private static class PageIndex extends FluentPage {

        @FindBy(className = "small")
        FluentList<FluentWebElement> smalls;

        @FindAll({@FindBy(id = "location"), @FindBy(className = "small")})
        FluentList<FluentWebElement> findAllElements;

        @Override
        public String getUrl() {
            return LocalFluentCase.DEFAULT_URL;
        }

        @Override
        public void isAt() {
            assertThat(getDriver().getTitle()).contains("Selenium");
        }
    }
}


