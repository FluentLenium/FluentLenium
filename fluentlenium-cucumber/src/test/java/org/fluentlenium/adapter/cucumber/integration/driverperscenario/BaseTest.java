package org.fluentlenium.adapter.cucumber.integration.driverperscenario;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage2;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.annotation.Page;

@FluentConfiguration(webDriver = "htmlunit")
public class BaseTest extends FluentCucumberTest {

    public BaseTest() {
        setWebDriver("htmlunit");
    }
}
