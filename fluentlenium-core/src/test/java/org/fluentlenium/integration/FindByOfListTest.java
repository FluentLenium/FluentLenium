package org.fluentlenium.integration;

import org.assertj.core.api.Assertions;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.support.FindBy;

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
}

class PageIndex extends FluentPage {

    @FindBy(className = "small")
    FluentList<FluentWebElement> smalls;

    @Override
    public String getUrl() {
        return LocalFluentCase.DEFAULT_URL;
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).contains("Selenium");
    }
}

