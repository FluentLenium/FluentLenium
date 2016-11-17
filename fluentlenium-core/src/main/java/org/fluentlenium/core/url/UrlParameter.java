package org.fluentlenium.core.url;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * URL Parameter.
 */
@EqualsAndHashCode(exclude = "repr")
@Getter
public class UrlParameter {

    private final String name;
    private boolean optional;

    /**
     * Creates a new parameter
     *
     * @param name parameter name
     */
    public UrlParameter(final String name) {
        this.name = name;
    }

    /**
     * Creates a new parameter
     *
     * @param name     parameter name
     * @param optional optional parameter
     */
    public UrlParameter(final String name, final boolean optional) {
        this.name = name;
        this.optional = optional;
    }

    @Override
    public String toString() {
        return (isOptional() ? "?" : "") + getName();
    }
}
