package org.fluentlenium.examples.pages.clickandopennew;

import java.util.concurrent.TimeUnit;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class GithubW3cLoggingPage extends FluentPage {

    @FindBy(className = "js-issue-title")
    private FluentWebElement title;

    @Override
    public void isAt() {
        await().atMost(5, TimeUnit.SECONDS).until(title).displayed();
    }

}
