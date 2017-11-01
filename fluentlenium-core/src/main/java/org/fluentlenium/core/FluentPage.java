package org.fluentlenium.core;

import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

import org.apache.commons.lang3.StringUtils;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.page.ClassAnnotations;
import org.fluentlenium.core.url.ParsedUrlTemplate;
import org.fluentlenium.core.url.UrlTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;

/**
 * Use the Page Object Pattern to have more resilient tests.
 * <p>
 * Extend this class and use @{@link PageUrl} and @{@link org.openqa.selenium.support.FindBy} annotations to provide
 * injectable Page Objects to FluentLenium.
 */
public class FluentPage extends DefaultFluentContainer implements FluentPageControl {

    private final ClassAnnotations classAnnotations = new ClassAnnotations(getClass());

    /**
     * Creates a new fluent page.
     */
    public FluentPage() {
        // Default constructor
    }

    /**
     * Creates a new fluent page, using given fluent control.
     *
     * @param control fluent control
     */
    public FluentPage(FluentControl control) {
        super(control);
    }

    @Override
    public String getUrl() {
        if (getClass().isAnnotationPresent(PageUrl.class)) {
            String url = getPageUrlValue(getClass().getAnnotation(PageUrl.class));

            if (!url.isEmpty()) {
                return url;
            }
        }
        return null;
    }

    private String getPageUrlValue(PageUrl pageUrl) {
        return  ((pageUrl.isLocalFile()) ? getAbsoluteUrlFromFile(pageUrl.file()) : StringUtils.EMPTY) + pageUrl.value();
    }

    @Override
    public String getUrl(Object... parameters) {
        String url = getUrl();
        if (url == null) {
            return null;
        }

        UrlTemplate template = new UrlTemplate(url);

        for (Object parameter : parameters) {
            template.add(parameter == null ? null : String.valueOf(parameter));
        }

        return template.render();
    }

    @Override
    public void isAt() {
        By by = classAnnotations.buildBy();
        if (by != null) {
            isAtUsingSelector(by);
        }

        String url = getUrl();
        if (url != null) {
            isAtUsingUrl(url);
        }
    }

    @Override
    public void isAt(Object... parameters) {
        String url = getUrl(parameters);
        if (url != null) {
            isAtUsingUrl(url);
        }
    }

    /**
     * URL matching implementation for isAt().
     *
     * @param urlTemplate URL Template
     */
    protected void isAtUsingUrl(String urlTemplate) {
        UrlTemplate template = new UrlTemplate(urlTemplate);

        String url = url();
        ParsedUrlTemplate parse = template.parse(url);

        if (!parse.matches()) {
            throw new AssertionError(String.format("Current URL [%s] doesn't match expected Page URL [%s]", url, urlTemplate));
        }
    }

    /**
     * Selector matching implementation for isAt().
     *
     * @param by by selector
     */
    protected void isAtUsingSelector(By by) {
        try {
            $(by).first().now();
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            throw new AssertionError("@FindBy element not found for page " + getClass().getName(), e);
        }
    }

    @Override
    public <P extends FluentPage> P go() {
        String url = getUrl();
        if (url == null) {
            throw new IllegalStateException(
                    "An URL should be defined on the page. Use @PageUrl annotation or override getUrl() method.");
        }
        goTo(url);
        return (P) this;
    }

    @Override
    public <P extends FluentPage> P go(Object... params) {
        String url = getUrl(params);
        if (url == null) {
            throw new IllegalStateException(
                    "An URL should be defined on the page. Use @PageUrl annotation or override getUrl() method.");
        }
        goTo(url);
        return (P) this;
    }

    @Override
    public ParsedUrlTemplate parseUrl() {
        return parseUrl(url());
    }

    @Override
    public ParsedUrlTemplate parseUrl(String url) {
        String templateUrl = getUrl();
        if (templateUrl == null) {
            throw new IllegalStateException(
                    "An URL should be defined on the page. Use @PageUrl annotation or override getUrl() method.");
        }

        UrlTemplate template = new UrlTemplate(templateUrl);
        ParsedUrlTemplate parse = template.parse(url);

        return parse;
    }
}
