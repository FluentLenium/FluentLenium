package org.fluentlenium.pages;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.openqa.selenium.support.FindBy;

public class IndexPage extends FluentPage {

    private FluentWebElement linkToPage2;

    @FindBy(css = "a.go-next")
    public FluentWebElement linkToPage2FoundWithFindBy;

    @Override
    public String getUrl() {
        return IntegrationFluentTest.DEFAULT_URL;
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).contains("Selenium");
    }

    public void goToNextPage() {
        linkToPage2.click();
    }

    public void goToNextPageWithFindByClassLink() {
        linkToPage2FoundWithFindBy.click();
    }
}
