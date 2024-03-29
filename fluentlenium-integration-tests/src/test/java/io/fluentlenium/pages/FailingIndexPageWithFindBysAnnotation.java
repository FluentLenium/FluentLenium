package io.fluentlenium.pages;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.test.IntegrationFluentTest;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

@FindBys({@FindBy(className = "notparent"), @FindBy(className = "child")})
public class FailingIndexPageWithFindBysAnnotation extends FluentPage {
    @Override
    public String getUrl() {
        return IntegrationFluentTest.DEFAULT_URL;
    }
}
