package io.fluentlenium.examples.pages.clickandopennew;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.PageUrl;
import io.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("https://www.awesome-testing.com/2019/01/adding-console-error-log-verification.html")
public class AwesomeTestingBlogPage extends FluentPage {

    @FindBy(partialLinkText = "W3C Webdriver")
    private FluentWebElement linkOpeningInNewPage;

    @Override
    public void isAt() {
        await().until(linkOpeningInNewPage).displayed();
    }

    public GithubW3cLoggingPage clickLinkAndSwitchWindow() {
        linkOpeningInNewPage.scrollToCenter();
        window().clickAndOpenNew(linkOpeningInNewPage);
        return newInstance(GithubW3cLoggingPage.class);
    }

}
