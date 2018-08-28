package org.fluentlenium.adapter.cucumber.integration.driverperscenario.steps;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage2;
import org.fluentlenium.core.annotation.Page;

public class BaseTest extends FluentCucumberTest {

    @Page
    protected LocalPage page;
    protected LocalPage2 page2;

    public BaseTest() {
        setWebDriver("htmlunit");
    }
}
