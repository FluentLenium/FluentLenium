package org.fluentlenium.example.spring.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

@PageUrl("/")
public class MainPage extends FluentPage {

    @FindBy(linkText = "Selenium")
    private FluentWebElement seleniumLink;

    @FindBy(className = "whats-fluentlenium")
    private FluentWebElement content;

    public MainPage verifyIfIsLoaded() {
        assertThat(content).isPresent();
        return this;
    }

    public void clickOnSeleniumLink() {
        seleniumLink.click();
    }
}
