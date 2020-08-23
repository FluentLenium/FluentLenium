package org.fluentlenium.core;

import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Stores a URL and URL parameters parsed from that URL for caching purposes in {@link FluentPage}.
 */
public final class PageUrlCache {

    private String url;
    private Map<String, String> parameters;

    public String getUrl() {
        return url;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * Returns the parameter value for the argument parameter name, or null if the parameter doesn't exist.
     *
     * @param parameterName the parameter name
     * @return the parameter value or null
     */
    public String getParameter(String parameterName) {
        return parameters.get(parameterName);
    }

    /**
     * Saves the argument url and parameters in this object.
     *
     * @param url        the url
     * @param parameters the url parameters
     */
    public void cache(String url, Map<String, String> parameters) {
        this.url = requireNonNull(url);
        this.parameters = requireNonNull(parameters);
    }
}
