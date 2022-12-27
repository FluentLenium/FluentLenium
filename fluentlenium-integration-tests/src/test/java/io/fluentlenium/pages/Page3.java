package io.fluentlenium.pages;

import io.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class Page3 extends IndexPage {

    @FindBy(css = "a.go-next")
    public FluentWebElement linkToPage2FoundWithFindByOnPage3;
}
