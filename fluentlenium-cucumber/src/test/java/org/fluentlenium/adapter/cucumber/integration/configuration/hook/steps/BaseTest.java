package org.fluentlenium.adapter.cucumber.integration.configuration.hook.steps;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;

import org.fluentlenium.adapter.cucumber.integration.configuration.hook.page.LocalWithHookPage;
import org.fluentlenium.adapter.cucumber.integration.configuration.hook.page.LocalWithHookPage2;
import org.fluentlenium.core.annotation.Page;

public class BaseTest extends FluentCucumberTest {

    @Page
    protected LocalWithHookPage page;

    @Page
    protected LocalWithHookPage2 page2;

}
