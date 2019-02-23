package org.fluentlenium.assertj.integration.page.pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

@FindAll({@FindBy(id = "oneline"), @FindBy(className = "small")})
public class IndexPage extends FluentPage {

    @FindBy(id = "oneline")
    private FluentWebElement fluentWebElement;

    @FindBy(className = "small")
    private FluentList<FluentWebElement> fluentList;

    public void verifyElement() {
        assertThat(this).hasElement(fluentWebElement);
    }

    public void verifyElements() {
        assertThat(this).hasElements(fluentList);
    }

    public void verifyElementDisplayed() {
        assertThat(this).hasElementDisplayed(fluentWebElement);
    }
}
