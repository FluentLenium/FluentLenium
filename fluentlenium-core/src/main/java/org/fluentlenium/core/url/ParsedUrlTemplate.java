package org.fluentlenium.core.url;

import java.util.Collections;
import java.util.Map;

/**
 * Parsed URL template.
 */
public class ParsedUrlTemplate {
    private final boolean matches;
    private final Map<String, String> parameters;

    /**
     * Creates a new url parameters parsed.
     *
     * @param matches    true if matches, false otherwise
     * @param parameters parameter values
     */
    ParsedUrlTemplate(final boolean matches, final Map<String, String> parameters) {
        this.matches = matches;
        this.parameters = Collections.unmodifiableMap(parameters);
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
