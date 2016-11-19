package org.fluentlenium.core.url;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple template engine for URL parameters.
 */
public class UrlParametersTemplate {
    private static final Pattern FORMAT_REGEX = Pattern.compile("\\{(.+?)\\}");
    private static final Pattern FORMAT_REGEX_OPTIONAL = Pattern.compile("/?\\{\\?(.+?)\\}");
    private final String template;

    private final List<String> parameterNames = new ArrayList<>();
    private final Map<String, UrlParameter> parameters = new LinkedHashMap<>();
    private final Map<String, String> parameterGroups = new LinkedHashMap<>();
    private final Map<UrlParameter, Object> properties = new LinkedHashMap<>();

    /**
     * Creates a new template
     *
     * @param template template string
     */
    public UrlParametersTemplate(final String template) {
        this.template = template;
        final Matcher matcher = FORMAT_REGEX.matcher(template);
        while (matcher.find()) {
            final String group = matcher.group(1);
            final UrlParameter parameter;
            if (group.startsWith("?")) {
                parameter = new UrlParameter(group.substring(1), true);
            } else {
                parameter = new UrlParameter(group, false);
            }
            parameters.put(parameter.getName(), parameter);
            parameterGroups.put(parameter.getName(), Pattern.quote(group));
            parameterNames.add(parameter.getName());
        }
    }

    /**
     * Remove all property values.
     */
    public void clear() {
        properties.clear();
    }

    /**
     * Get all declared parameter parameters of this template.
     *
     * @return declared parameter parameters
     */
    public List<UrlParameter> getParameters() {
        return new ArrayList<>(parameters.values());
    }

    /**
     * Add property value.
     *
     * @param value property value
     * @return {@code this} reference to chain calls
     */
    public UrlParametersTemplate add(final Object value) {
        properties.put(parameters.get(parameterNames.get(properties.size())), value);
        return this;
    }

    /**
     * Set property value.
     *
     * @param name  name
     * @param value value
     * @return {@code this} reference to chain calls
     */
    public UrlParametersTemplate put(final String name, final Object value) {
        final UrlParameter parameter = parameters.get(name);
        if (parameter == null) {
            throw new IllegalArgumentException("Invalid parameter name: " + name);
        }
        properties.put(parameter, value);
        return this;
    }

    /**
     * Add all property properties
     *
     * @param values property properties
     * @return {@code this} reference to chain calls
     */
    public void addAll(final List<Object> values) {
        for (final Object value : values) {
            add(value);
        }
    }

    /**
     * Set property properties.
     *
     * @param values properties
     * @return {@code this} reference to chain calls
     */
    public UrlParametersTemplate put(final Map<String, Object> values) {
        values.putAll(values);
        return this;
    }

    /**
     * Render the string.
     *
     * @return
     */
    public String render() {
        String rendered = template;
        for (final UrlParameter parameter : parameters.values()) {
            final Object value = properties.get(parameter);
            if (value == null) {
                if (parameter.isOptional()) {
                    rendered = rendered.replaceAll(String.format("/\\{%s\\}", parameterGroups.get(parameter.getName())), "");
                } else {
                    throw new IllegalStateException(String.format("Parameter %s is not defined", parameter.getName()));
                }
            } else {
                rendered = rendered
                        .replaceAll(String.format("\\{%s\\}", parameterGroups.get(parameter.getName())), String.valueOf(value));
            }

        }
        return rendered;
    }

    /**
     * Get properties from string
     *
     * @param input string
     * @return properties
     */
    public UrlParametersParsed parse(final String input) {
        final LinkedHashMap<String, String> parsedValues = new LinkedHashMap<>();

        final Pattern inputRegex = Pattern.compile(template.replaceAll(FORMAT_REGEX_OPTIONAL.pattern(), "(?:/([^/]+))?")
                .replaceAll(FORMAT_REGEX.pattern(), "([^/]+)") + "/?");

        final Matcher matcher = inputRegex.matcher(input);
        final boolean matches = matcher.matches();

        if (matches) {
            for (int i = 0; i < parameterNames.size() && i < matcher.groupCount(); i++) {
                final String value = matcher.group(i + 1);
                if (value != null) {
                    parsedValues.put(parameterNames.get(i), value);
                }
            }
        }

        return new UrlParametersParsed(input, matches, parsedValues);
    }

}
