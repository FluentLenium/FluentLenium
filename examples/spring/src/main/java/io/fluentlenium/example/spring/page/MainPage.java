package io.fluentlenium.example.spring.page;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.PageUrl;
import io.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

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
