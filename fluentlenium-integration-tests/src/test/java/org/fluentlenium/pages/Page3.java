package org.fluentlenium.pages;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class Page3 extends IndexPage {

    @FindBy(css = "a.go-next")
    public /* default */ FluentWebElement linkToPage2FoundWithFindByOnPage3;
}
