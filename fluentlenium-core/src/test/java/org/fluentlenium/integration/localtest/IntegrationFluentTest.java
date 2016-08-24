package org.fluentlenium.integration.localtest;

import org.fluentlenium.integration.util.adapter.FluentTest;

import static org.fluentlenium.integration.util.UrlUtil.getAbsoluteUrlFromFile;
import static org.fluentlenium.integration.util.UrlUtil.getAbsoluteUrlPathFromFile;

public abstract class IntegrationFluentTest extends FluentTest {

    public static final String DEFAULT_URL;
    public static final String DEFAULT_URL_PATH;
    public static final String JAVASCRIPT_URL;
    public static final String PAGE_2_URL;
    public static final String IFRAME_URL;
    public static final String ANOTHERPAGE_URL;

    static {
        DEFAULT_URL = getAbsoluteUrlFromFile("index.html");
        DEFAULT_URL_PATH = getAbsoluteUrlPathFromFile("index.html");
        JAVASCRIPT_URL = getAbsoluteUrlFromFile("javascript.html");
        PAGE_2_URL = getAbsoluteUrlFromFile("page2.html");
        IFRAME_URL = getAbsoluteUrlFromFile("iframe.html");
        ANOTHERPAGE_URL = getAbsoluteUrlFromFile("anotherpage.html");
    }

    @Override
    public String getWebDriver() {
        return "htmlunit";
    }
}
