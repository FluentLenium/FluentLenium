package io.fluentlenium.test;

import io.fluentlenium.utils.UrlUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.junit.jupiter.api.BeforeAll;

import static io.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;
import static io.fluentlenium.utils.UrlUtils.getAbsoluteUrlPathFromFile;

/**
 * Base class for integration tests.
 * <p>
 * Uses {@code htmlunit} as the underlying driver and provides local HTML files that you can use as test pages.
 */
public abstract class IntegrationFluentTest extends FluentTest {

    public static final String ANOTHER_PAGE_URL = UrlUtils.getAbsoluteUrlFromFile("anotherpage.html");
    public static final String COMPONENTS_URL = UrlUtils.getAbsoluteUrlFromFile("components.html");
    public static final String COUNT_URL = UrlUtils.getAbsoluteUrlFromFile("count.html");
    public static final String DEFAULT_URL = UrlUtils.getAbsoluteUrlFromFile("index.html");
    public static final String SHADOW_URL = UrlUtils.getAbsoluteUrlFromFile("shadow.html");
    public static final String DEFAULT_URL_PATH = UrlUtils.getAbsoluteUrlPathFromFile("index.html");
    public static final String IFRAME_URL = UrlUtils.getAbsoluteUrlFromFile("iframe.html");
    public static final String JAVASCRIPT_URL = UrlUtils.getAbsoluteUrlFromFile("javascript.html");
    public static final String PAGE_2_URL = UrlUtils.getAbsoluteUrlFromFile("page2.html");
    public static final String PAGE_2_URL_TEST = UrlUtils.getAbsoluteUrlFromFile("page2url.html");
    public static final String SIZE_CHANGE_URL = UrlUtils.getAbsoluteUrlFromFile("size-change.html");
    public static final String ELEMENT_REPLACE_URL = UrlUtils.getAbsoluteUrlFromFile("element-replace.html");
    public static final String DISAPPEARING_EL_URL = UrlUtils.getAbsoluteUrlFromFile("disappear.html");
    public static final String CLICK_URL = UrlUtils.getAbsoluteUrlFromFile("click.html");

    @BeforeAll
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

}
