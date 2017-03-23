package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.support.FindBy;
import static org.fluentlenium.integration.util.UrlUtil.getAbsoluteUrlFromFile;

public class WaitSizeTest extends IntegrationFluentTest {

    @FindBy(css = ".row")
    private FluentList<FluentWebElement> rows;

    @Test
    public void checkWithNameCssSelector() {
        goTo(getAbsoluteUrlFromFile("size-change.html"));
        assert($(".row").size() == 2);
        await().until($(".row")).size().greaterThan(2);
    }


    @Test
    public void checkWithFindByNameCssSelector() {
        goTo(getAbsoluteUrlFromFile("size-change.html"));
        assert(rows.size() == 2);
        await().until(rows).size().greaterThan(2);
    }
}

