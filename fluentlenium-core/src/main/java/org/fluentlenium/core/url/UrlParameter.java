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
    private final String group;
    private final String path;
    private final String match;
    private final boolean optional;


    /**
     * Creates a new parameter
     *
     * @param name     parameter name
     * @param group parameter match group
     * @param path  parameter path
     * @param optional optional parameter
     */
    public UrlParameter(final String name, final String group, final String path, final String match, final boolean optional) {
        this.name = name;
        this.group = group;
        this.path = path;
        this.match = match;
        this.optional = optional;
    }

    @Override
    public String toString() {
        return (isOptional() ? "?" : "") + getName();
    }
}
