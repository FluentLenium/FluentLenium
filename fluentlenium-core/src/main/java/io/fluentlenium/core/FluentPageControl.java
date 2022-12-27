package io.fluentlenium.core;

import io.fluentlenium.core.url.ParsedUrlTemplate;import io.fluentlenium.core.url.ParsedUrlTemplate;

/**
 * Provides controls for a Page Object.
 *
 * @see FluentPage
 */
public interface FluentPageControl extends FluentControl {

    /**
     * Gets the URL of the page.
     * It can contain mandatory parameters <code>{param}</code> and optional parameters <code>{?param1}</code>.
     *
     * @return page URL
     */
    String getUrl();

    /**
     * URL of the page, after replacing parameters with given values.
     *
     * @param parameters parameter values
     * @return Effective url generated for given parameter values
     * @throws IllegalArgumentException if some required parameters are missing
     */
    String getUrl(Object... parameters);

    /**
     * Check if the browser is on this page.
     */
    void isAt();

    /**
     * Check if the browser is on this page, after replacing parameters with given values.
     *
     * @param parameters list of parameters
     */
    void isAt(Object... parameters);

    /**
     * Go to the url defined in the page
     *
     * @param <P> the fluent page
     * @return <P> FluentPage object
     */
    <P extends FluentPage> P go(); // NOPMD ShortMethodName

    /**
     * Got to the url defined in the page, using given parameters.
     *
     * @param params page url parameter values
     * @param <P> the fluent page
     * @return <P> FluentPage object
     * @throws IllegalArgumentException if some required parameters are missing
     */
    <P extends FluentPage> P go(Object... params);

    /**
     * Get the parameter values of page URL extracted from current URL.
     *
     * @return parameter values
     */
    ParsedUrlTemplate parseUrl();

    /**
     * Get the parameter values of page URL extracted from given URL.
     *
     * @param url url to parse
     * @return parameter values
     */
    ParsedUrlTemplate parseUrl(String url);
}
