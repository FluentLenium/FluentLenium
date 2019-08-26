package org.fluentlenium.examples.pages;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.action.MouseElementActions;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("https://duckduckgo.com")
public class DuckDuckMainPage extends FluentPage {
    private static final String SEARCH_FORM_HOMEPAGE = "#search_form_homepage";

    @FindBy(css = "#search_form_input_homepage")
    private FluentWebElement searchInput;

    @FindBy(css = "#search_button_homepage")
    private FluentWebElement searchButton;

    // This doesn't work as well
    @FindBy(css = "#search_button_homepage")
    private WebElement searchButtonWebElement;

    public DuckDuckMainPage typeSearchPhraseIn(String searchPhrase) {
        searchInput.write(searchPhrase);
        return this;
    }

    public DuckDuckMainPage submitSearchForm() {
        searchButton.submit();
        awaitUntilSearchFormDisappear();
        return this;
    }

    public void assertIsPhrasePresentInTheResults(String searchPhrase) {
        assertThat(window().title()).contains(searchPhrase);
    }

    public DuckDuckMainPage testFindByFluentWebElementActions(String searchPhrase) {
        inputSearchPhrase(searchPhrase);
        searchButton.mouse().moveToElement().click();

        return this;
    }

    public DuckDuckMainPage testFluentWebElementActions(String searchPhrase) {
        inputSearchPhrase(searchPhrase);
        new MouseElementActions(getDriver(), searchButton).moveToElement().click();

        return this;
    }

    private DuckDuckMainPage awaitUntilSearchFormDisappear() {
        await().atMost(5, TimeUnit.SECONDS).until(el(SEARCH_FORM_HOMEPAGE)).not().present();
        return this;
    }

    private void inputSearchPhrase(String searchPhrase) {
        await().until(searchInput).clickable();
        searchInput.fill().with(searchPhrase);
    }
}
