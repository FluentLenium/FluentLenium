package org.fluentlenium.example.spring.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class ResultsPage extends FluentPage {

    @FindBy(css = ".srg")
    private FluentWebElement results;

    public void waitForResults() {
        await().until(results).displayed();
    }
}
