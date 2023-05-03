package io.fluentlenium.pages;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.test.IntegrationFluentTest;
import org.openqa.selenium.support.FindBy;

@FindBy(id = "notoneline")
public class FailingIndexPageWithFindByAnnotation extends FluentPage {
    @Override
    public String getUrl() {
        return IntegrationFluentTest.DEFAULT_URL;
    }
}

