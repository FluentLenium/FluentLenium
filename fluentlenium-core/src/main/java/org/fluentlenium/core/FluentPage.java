package org.fluentlenium.core;

import org.apache.commons.lang3.StringUtils;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.page.ClassAnnotations;
import org.fluentlenium.core.url.ParsedUrlTemplate;
import org.fluentlenium.core.url.UrlTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;

import static org.fluentlenium.utils.Preconditions.checkArgumentBlank;
import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

/**
 * Use the Page Object Pattern to have more resilient tests.
 * <p>
 * Extend this class and use @{@link PageUrl} and @{@link org.openqa.selenium.support.FindBy} annotations to provide
 * injectable Page Objects to FluentLenium.
 */
public class FluentPage extends DefaultFluentContainer implements FluentPageControl {

    private final ClassAnnotations classAnnotations = new ClassAnnotations(getClass());

    private final PageUrlCache pageUrlCache = new PageUrlCache();

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

    public ClassAnnotations getClassAnnotations() {
        return classAnnotations;
    }

    @Override
    public String getUrl() {
        String pageUrl = null;
        if (getClass().isAnnotationPresent(PageUrl.class)) {
            String url = getPageUrlValue(getClass().getAnnotation(PageUrl.class));

            if (!url.isEmpty()) {
                pageUrl = url;
            }
        }
        return pageUrl;
    }

    /**
     * Parses the current URL and returns the parameter value for the argument parameter name.
     * <p>
     * In case the parameter is not defined in the {@link PageUrl} annotation,
     * or the parameter (mandatory or optional) has no value in the actual URL, this method returns {@code null}.
     * <p>
     * There is also caching in place to improve performance.
     * It compares the current URL with the cached URL, and if they are the same,
     * the parameter is returned from the cached values, otherwise the URL is parsed again and the parameters
     * are returned from the new URL.
     * <p>
     * Examples (for template + URL + paramName combinations):
     * <pre>
     * "/abc/{param1}/def/{param2}"  + "/abc/param1val/def/param2val" + "param1" -> "param1val"
     * "/abc/{param1}/def/{param2}"  + "/abc/param1val/def/param2val" + "param4" -> null
     * "/abc{?/param1}/def/{param2}" + "/abc/param1val/def/param2val" + "param1" -> "param1val"
     * "/abc{?/param1}/def/{param2}" + "/abc/def/param2val"           + "param1" -> null
     * </pre>
     *
     * @param parameterName the parameter to get the value of
     * @return the desired parameter value or null if a value for the given parameter name is not present
     * @throws IllegalArgumentException when the argument param is null or empty
     */
    public String getParam(String parameterName) {
        checkArgumentBlank(parameterName, "The parameter name to query should not be blank.");
        String url = url();

        if (!url.equals(pageUrlCache.getUrl())) {
            pageUrlCache.cache(url, parseUrl(url).parameters());
        }
        return pageUrlCache.getParameter(parameterName);
    }

    private String getPageUrlValue(PageUrl pageUrl) {
        return (isLocalFile(pageUrl) ? getAbsoluteUrlFromFile(pageUrl.file()) : StringUtils.EMPTY) + pageUrl.value();
    }

    private boolean isLocalFile(PageUrl pageUrl) {
        return pageUrl != null && !pageUrl.file().isEmpty();
    }

    private PageUrl getPageUrlAnnotation() {
        PageUrl annotation = null;
        if (getClass().isAnnotationPresent(PageUrl.class)) {
            annotation = getClass().getAnnotation(PageUrl.class);
        }
        return annotation;
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
     * <p>
     * If there is a {@link PageUrl} annotation applied on the class and it has the {@code file} attribute defined this method
     * will skip the url parsing to skip URL check because it is not able to get local file path relatively.
     *
     * @param urlTemplate URL Template
     * @throws AssertionError when the current URL doesn't match the expected page URL
     */
    public void isAtUsingUrl(String urlTemplate) {
        if (!isLocalFile(getPageUrlAnnotation())) {
            UrlTemplate template = new UrlTemplate(urlTemplate);

            String url = url();
            ParsedUrlTemplate parse = template.parse(url);

            if (!parse.matches()) {
                throw new AssertionError(
                        String.format("Current URL [%s] doesn't match expected Page URL [%s]", url, urlTemplate));
            }
        }
    }

    /**
     * Selector matching implementation for isAt().
     *
     * @param by by selector
     * @throws AssertionError if the element using the argument By is not found for the current page
     */
    public void isAtUsingSelector(By by) {
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

        return new UrlTemplate(templateUrl).parse(url);
    }
}
