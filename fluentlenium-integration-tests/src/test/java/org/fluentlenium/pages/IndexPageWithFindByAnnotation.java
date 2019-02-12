package org.fluentlenium.pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.test.IntegrationFluentTest;
import org.openqa.selenium.support.FindBy;

@FindBy(id = "oneline")
public class IndexPageWithFindByAnnotation extends FluentPage {
    @Override
    public String getUrl() {
        return IntegrationFluentTest.DEFAULT_URL;
    }
}
