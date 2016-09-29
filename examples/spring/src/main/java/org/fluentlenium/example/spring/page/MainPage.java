package org.fluentlenium.example.spring.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("/")
public class MainPage extends FluentPage {
    @FindBy(css = ".sbibod ")
    private FluentWebElement searchInputBorder;
    @FindBy(css = ".gsfi ")
    private FluentWebElement searchInput;
    @FindBy(css = "button[name=\"btnG\"]")
    private FluentWebElement searchButton;
    @Page
    private ResultsPage resultsPage;

    public MainPage typeTextIn() {
        searchInputBorder.click();
        searchInput.write("Something");
        return this;
    }

    public ResultsPage startSearch() {
        searchButton.click();
        return resultsPage;
    }
}
