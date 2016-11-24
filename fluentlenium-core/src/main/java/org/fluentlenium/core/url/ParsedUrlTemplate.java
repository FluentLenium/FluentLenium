package org.fluentlenium.core.url;

import org.apache.http.NameValuePair;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Parsed URL template.
 */
public class ParsedUrlTemplate {
    private final boolean matches;
    private final Map<String, String> parameters;
    private final List<NameValuePair> queryParameters;

    /**
     * Creates a new url parameters parsed.
     *
     * @param matches    true if matches, false otherwise
     * @param parameters parameter values
     */
    ParsedUrlTemplate(final boolean matches, final Map<String, String> parameters, final List<NameValuePair> queryParameters) {
        this.matches = matches;
        this.parameters = Collections.unmodifiableMap(parameters);
        this.queryParameters = queryParameters;
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

    /**
     * Get query string parameter values.
     *
     * @return list of name value pair
     */
    public List<NameValuePair> queryParameters() {
        return queryParameters;
    }
}
