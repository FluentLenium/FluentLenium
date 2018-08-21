package org.fluentlenium.adapter.cucumber.integration.configuration.annotation.steps;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.core.annotation.Page;

public class BaseTest extends FluentCucumberTest {

    @Page
    protected LocalPage page;

    @Page
    protected LocalPage page2;

}
