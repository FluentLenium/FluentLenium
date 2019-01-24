package org.fluentlenium.pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.test.IntegrationFluentTest;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

@FindAll({@FindBy(id = "oneline"), @FindBy(className = "small")})
public class IndexPageWithFindAllAnnotation extends FluentPage {
    @Override
    public String getUrl() {
        return IntegrationFluentTest.DEFAULT_URL;
    }
}
