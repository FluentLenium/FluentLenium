package io.fluentlenium.examples.cucumber.pageobject.page;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.PageUrl;
import io.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("https://duckduckgo.com")
public class HomePage extends FluentPage {

    @FindBy(css = "#search_form_input_homepage")
    private FluentWebElement searchInput;
    @FindBy(css = "#search_button_homepage")
    private FluentWebElement searchButton;

    public void find(String phrase) {
        searchInput.fill().with(phrase);
        searchButton.submit();
    }

}
