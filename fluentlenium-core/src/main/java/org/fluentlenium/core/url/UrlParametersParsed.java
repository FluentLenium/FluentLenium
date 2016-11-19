package org.fluentlenium.core.url;

import java.util.Collections;
import java.util.Map;

/**
 * Parsed URL.
 */
public class UrlParametersParsed {
    private final String url;
    private final boolean matches;
    private final Map<String, String> parameters;

    /**
     * Creates a new url parameters parsed.
     *
     * @param url        url
     * @param matches    true if matches, false otherwise
     * @param parameters parameter values
     */
    UrlParametersParsed(final String url, final boolean matches, final Map<String, String> parameters) {
        this.url = url;
        this.matches = matches;
        this.parameters = Collections.unmodifiableMap(parameters);
    }

    /**
     * Url containing parameters.
     *
     * @return url
     */
    public String url() {
        return this.url;
    }

    /**
     * Does it match the template.
     *
     * @return true if given url match
     */
    public boolean matches() {
        return matches;
    }

    /**
     * Get parameter values.
     *
     * @return unmodifiable map of parameters
     */
    public Map<String, String> parameters() {
        return parameters;
    }

}
