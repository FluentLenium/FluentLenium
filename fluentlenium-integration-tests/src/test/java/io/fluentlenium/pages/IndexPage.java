package io.fluentlenium.pages;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

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
