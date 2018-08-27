package org.fluentlenium.adapter.cucumber.integration.configuration.inheritance;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.configuration.FluentConfiguration;

@FluentConfiguration(webDriver = "htmlunit")
public class BaseTest extends FluentCucumberTest {

}
