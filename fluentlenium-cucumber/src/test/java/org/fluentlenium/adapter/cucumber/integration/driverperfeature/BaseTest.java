package org.fluentlenium.adapter.cucumber.integration.driverperfeature;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.annotation.Page;

import static org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.JVM;

@FluentConfiguration(webDriver = "htmlunit", driverLifecycle = JVM)
public class BaseTest extends FluentCucumberTest {

    @Page
    protected LocalPage page;

    @Page
    protected LocalPage page2;

}
