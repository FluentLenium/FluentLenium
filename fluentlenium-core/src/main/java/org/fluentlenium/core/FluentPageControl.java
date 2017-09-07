package org.fluentlenium.core;

import org.fluentlenium.core.url.ParsedUrlTemplate;

/**
 * Control a Page Object.
 *
 * @see FluentPage
 */
public interface FluentPageControl<P extends FluentPage> extends FluentControl {

    /**
     * URL of the page
     * It can contains mandatory parameters <code>{param}</code> and optional parameters <code>{param1}</code>
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
     */
    void isAt(Object... parameters);

    /**
     * Go to the url defined in the page
     */
    P go(); // NOPMD ShortMethodName

    /**
     * Got to the url defined in the page, using given parameters.
     *
     * @param params page url parameter values
     * @throws IllegalArgumentException if some required parameters are missing
     */
    P go(Object... params);

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
