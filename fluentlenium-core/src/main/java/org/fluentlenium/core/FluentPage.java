package org.fluentlenium.core;

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
    public FluentPage(final FluentControl control) {
        super(control);
    }

    @Override
    public String getUrl() {
        if (this.getClass().isAnnotationPresent(PageUrl.class)) {
            final String url = this.getClass().getAnnotation(PageUrl.class).value();
            if (!url.isEmpty()) {
                return url;
            }
        }
        return null;
    }

    @Override
    public String getUrl(final Object... parameters) {
        final String url = getUrl();
        if (url == null) {
            return null;
        }

        final UrlTemplate template = new UrlTemplate(url);

        for (final Object parameter : parameters) {
            template.add(parameter == null ? null : String.valueOf(parameter));
        }

        return template.render();
    }

    @Override
    public void isAt() {
        final By by = classAnnotations.buildBy();
        if (by != null) {
            isAtUsingSelector(by);
        }

        final String url = getUrl();
        if (url != null) {
            isAtUsingUrl(url);
        }
    }

    /**
     * URL matching implementation for isAt().
     *
     * @param urlTemplate URL Template
     */
    protected void isAtUsingUrl(final String urlTemplate) {
        final UrlTemplate template = new UrlTemplate(urlTemplate);

        final String url = url();
        final ParsedUrlTemplate parse = template.parse(url);

        if (!parse.matches()) {
            throw new AssertionError(String.format("Current URL [%s] doesn't match expected Page URL [%s]", url, urlTemplate));
        }
    }

    /**
     * Selector matching implementation for isAt().
     *
     * @param by by selector
     */
    protected void isAtUsingSelector(final By by) {
        try {
            $(by).first().now();
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            throw new AssertionError("@FindBy element not found for page " + getClass().getName(), e);
        }
    }

    @Override
    public final void go() {
        final String url = getUrl();
        if (url == null) {
            throw new IllegalStateException(
                    "An URL should be defined on the page. Use @PageUrl annotation or override getUrl() method.");
        }
        goTo(url);
    }

    @Override
    public void go(final Object... params) {
        final String url = getUrl(params);
        if (url == null) {
            throw new IllegalStateException(
                    "An URL should be defined on the page. Use @PageUrl annotation or override getUrl() method.");
        }
        goTo(url);
    }

    @Override
    public ParsedUrlTemplate parseUrl() {
        return parseUrl(url());
    }

    @Override
    public ParsedUrlTemplate parseUrl(final String url) {
        final String templateUrl = getUrl();
        if (templateUrl == null) {
            throw new IllegalStateException(
                    "An URL should be defined on the page. Use @PageUrl annotation or override getUrl() method.");
        }

        final UrlTemplate template = new UrlTemplate(templateUrl);
        final ParsedUrlTemplate parse = template.parse(url);

        return parse;
    }
}
