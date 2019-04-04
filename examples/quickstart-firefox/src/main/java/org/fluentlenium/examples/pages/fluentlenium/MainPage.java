package org.fluentlenium.examples.pages.fluentlenium;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.examples.components.fluentlenium.Header;
import org.openqa.selenium.support.FindBy;

@PageUrl("https://fluentlenium.com")
public class MainPage extends FluentPage {

    @FindBy(className = "whats-fluentlenium")
    private FluentWebElement mainContent;

    @FindBy(css = "nav")
    private Header header;

    @Override
    public void isAt() {
        assertThat(mainContent).isDisplayed();
    }

    public Header getHeader() {
        return header;
    }

}
