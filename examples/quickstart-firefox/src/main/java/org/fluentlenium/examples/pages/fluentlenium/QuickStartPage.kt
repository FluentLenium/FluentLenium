package org.fluentlenium.examples.pages.fluentlenium;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.examples.components.fluentlenium.Header;
import org.openqa.selenium.support.FindBy;

@PageUrl("https://fluentlenium.com/quickstart/")
public class QuickStartPage extends FluentPage {

    @FindBy(id = "table-of-contents")
    private FluentWebElement tableOfContents;

    @FindBy(css = "nav")
    private Header header;

    @Override
    public void isAt() {
        assertThat(tableOfContents).isDisplayed();
    }

    public Header getHeader() {
        return header;
    }

}
