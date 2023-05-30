package io.fluentlenium.examples.pages;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.PageUrl;
import io.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@PageUrl("https://duckduckgo.com")
public class DuckDuckMainPage extends FluentPage {
    private static final String SEARCH_FORM_HOMEPAGE = "#search_form_homepage";

    @FindBy(css = "[name=q]")
    private FluentWebElement searchInput;

    @FindBy(css = "#search_button_homepage")
    private FluentWebElement searchButton;

    public DuckDuckMainPage typeSearchPhraseIn(String searchPhrase) {
        await().until(searchInput).enabled();
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

    private DuckDuckMainPage awaitUntilSearchFormDisappear() {
        await().atMost(5, TimeUnit.SECONDS).until(el(SEARCH_FORM_HOMEPAGE)).not().present();
        return this;
    }
}
