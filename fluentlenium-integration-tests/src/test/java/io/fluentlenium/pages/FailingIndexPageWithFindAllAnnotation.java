package io.fluentlenium.pages;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.test.IntegrationFluentTest;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

@FindAll({@FindBy(id = "notoneline"), @FindBy(className = "notsmall")})
public class FailingIndexPageWithFindAllAnnotation extends FluentPage {
    @Override
    public String getUrl() {
        return IntegrationFluentTest.DEFAULT_URL;
    }
}
