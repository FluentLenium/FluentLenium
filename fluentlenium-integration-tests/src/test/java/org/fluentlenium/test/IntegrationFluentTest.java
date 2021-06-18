package org.fluentlenium.test;

import org.fluentlenium.adapter.junit.jupiter.FluentTest;

import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;
import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlPathFromFile;

/**
 * Base class for integration tests.
 * <p>
 * Uses {@code htmlunit} as the underlying driver and provides local HTML files that you can use as test pages.
 */
public abstract class IntegrationFluentTest extends FluentTest {

    public static final String ANOTHER_PAGE_URL = getAbsoluteUrlFromFile("anotherpage.html");
    public static final String COMPONENTS_URL = getAbsoluteUrlFromFile("components.html");
    public static final String COUNT_URL = getAbsoluteUrlFromFile("count.html");
    public static final String DEFAULT_URL = getAbsoluteUrlFromFile("index.html");
    public static final String DEFAULT_URL_PATH = getAbsoluteUrlPathFromFile("index.html");
    public static final String IFRAME_URL = getAbsoluteUrlFromFile("iframe.html");
    public static final String JAVASCRIPT_URL = getAbsoluteUrlFromFile("javascript.html");
    public static final String PAGE_2_URL = getAbsoluteUrlFromFile("page2.html");
    public static final String PAGE_2_URL_TEST = getAbsoluteUrlFromFile("page2url.html");
    public static final String SIZE_CHANGE_URL = getAbsoluteUrlFromFile("size-change.html");
    public static final String ELEMENT_REPLACE_URL = getAbsoluteUrlFromFile("element-replace.html");
    public static final String DISAPPEARING_EL_URL = getAbsoluteUrlFromFile("disappear.html");
    public static final String CLICK_URL = getAbsoluteUrlFromFile("click.html");

    @Override
    public String getWebDriver() {
        return "htmlunit";
    }

}
