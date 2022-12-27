package io.fluentlenium.core.url;

/**
 * URL Parameter.
 */
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
     * @param group    parameter match group
     * @param path     parameter path
     * @param match    parameter matche
     * @param optional optional parameter
     */
    public UrlParameter(String name, String group, String path, String match, boolean optional) {
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

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getPath() {
        return path;
    }

    public String getMatch() {
        return match;
    }

    public boolean isOptional() {
        return optional;
    }
}
