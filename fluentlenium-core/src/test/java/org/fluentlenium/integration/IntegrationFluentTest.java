package org.fluentlenium.integration;

import org.fluentlenium.integration.adapter.FluentTest;

import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

public class IntegrationFluentTest extends FluentTest {

    protected static final String DEFAULT_URL = getAbsoluteUrlFromFile("index.html");

}
