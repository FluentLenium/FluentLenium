package org.fluentlenium.adapter.junit.integration;

import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

import org.fluentlenium.adapter.junit.FluentTest;

public class IntegrationFluentTest extends FluentTest {

    protected static final String DEFAULT_URL = getAbsoluteUrlFromFile("index.html");

}
