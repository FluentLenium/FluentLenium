package io.fluentlenium.core.url;

import com.google.common.collect.ImmutableList;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple template engine for URL parameters.
 */
public class UrlTemplate {
    private static final Pattern PARAM_REGEX = Pattern.compile("\\{(.+?)\\}");
    private final String template;

    private final List<String> parameterNames = new ArrayList<>();
    private final Map<String, UrlParameter> parameters = new LinkedHashMap<>();
    private final Map<UrlParameter, String> properties = new LinkedHashMap<>();

    /**
     * Creates a new template
     *
     * @param template template string
     */
    public UrlTemplate(String template) {
        this.template = template;
        Matcher matcher = PARAM_REGEX.matcher(template);
        while (matcher.find()) {
            String match = matcher.group(0);
            String group = matcher.group(1);

            boolean optional = group.startsWith("?");
            int lastIndex = group.lastIndexOf('/');

            String parameterName;
            String path = null;
            if (lastIndex > -1 && lastIndex < group.length()) {
                path = group.substring(optional ? 1 : 0, lastIndex + 1);
                parameterName = group.substring(lastIndex + 1);
            } else if (group.startsWith("?")) {
                parameterName = group.substring(1);
            } else {
                parameterName = group;
            }

            UrlParameter parameter = new UrlParameter(parameterName, group, path, match, optional);

            if (parameters.containsKey(parameter.getName())) {
                throw new IllegalStateException(
                        String.format("Multiple parameters are defined with the same name (%s).", parameter.getName()));
            }

            parameters.put(parameter.getName(), parameter);
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
    public UrlTemplate add(String value) {
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
    public UrlTemplate put(String name, String value) {
        UrlParameter parameter = parameters.get(name);
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
    public UrlTemplate addAll(List<String> values) {
        for (String value : values) {
            add(value);
        }
        return this;
    }

    /**
     * Set property properties.
     *
     * @param values properties
     * @return {@code this} reference to chain calls
     */
    public UrlTemplate put(Map<String, String> values) {
        values.forEach(this::put);
        return this;
    }

    /**
     * Render the string.
     *
     * @return rendered url, based on template with parameters applied.
     */
    public String render() {
        String rendered = template;
        for (UrlParameter parameter : parameters.values()) {
            Object value = properties.get(parameter);
            String group = parameter.getGroup();
            if (value == null && !parameter.isOptional()) {
                throw new IllegalArgumentException(String.format("Value for parameter %s is missing.", parameter));
            } else {
                rendered = rendered.replaceAll(Pattern.quote(String.format("{%s}", group)),
                        buildRenderReplacement(parameter, value == null ? null : String.valueOf(value)));
            }

        }
        return rendered;
    }

    private String buildRenderReplacement(UrlParameter parameter, String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        String path = parameter.getPath();
        if (path != null) {
            return path + value;
        }
        return value;
    }

    private String buildParsePattern() {
        String fixedTemplate = template;
        fixedTemplate = escapeQuestionMarkQuanitifiers(fixedTemplate);
        fixedTemplate = escapeQuantifiers(fixedTemplate);
        fixedTemplate = ignoreLeadingSlash(fixedTemplate);
        fixedTemplate = ignoreEndingSlash(fixedTemplate);
        fixedTemplate = replaceParameters(fixedTemplate);

        return fixedTemplate;
    }

    private String escapeQuestionMarkQuanitifiers(String fixedTemplate) {
        if (fixedTemplate.contains("?") && !fixedTemplate.contains("?/")) {
            fixedTemplate = fixedTemplate.replace("?", "\\?");
        }
        return fixedTemplate;
    }

    private String escapeQuantifiers(String fixedTemplate) {
        List<String> quantifiers = ImmutableList.of("+", "*");
        for (String quant : quantifiers) {
            fixedTemplate = fixedTemplate.replace(quant, "\\" + quant);
        }
        return fixedTemplate;
    }

    private String replaceParameters(String fixedTemplate) {
        for (UrlParameter parameter : parameters.values()) {
            String replacementPattern = "%s([^/]+)";
            if (parameter.isOptional()) {
                replacementPattern = String.format("(?:%s)?", replacementPattern);
            }
            fixedTemplate = fixedTemplate.replaceAll(Pattern.quote(parameter.getMatch()),
                    String.format(replacementPattern, parameter.getPath() == null ? "" : parameter.getPath()));
        }
        return fixedTemplate;
    }

    private String ignoreEndingSlash(String fixedTemplate) {
        if (fixedTemplate.endsWith("/")) {
            return fixedTemplate + "?";
        }

        return fixedTemplate + "/?";
    }

    private String ignoreLeadingSlash(String fixedTemplate) {
        if (fixedTemplate.startsWith("/")) {
            return fixedTemplate.replaceFirst("/", "/?");
        }

        return "/?" + fixedTemplate;
    }

    /**
     * Get properties from string
     *
     * @param input string
     * @return properties
     */
    public ParsedUrlTemplate parse(String input) {
        String noQueryInput;
        List<NameValuePair> queryParameters;

        try {
            URIBuilder uriBuilder = new URIBuilder(input);
            queryParameters = uriBuilder.getQueryParams();
            uriBuilder.setCustomQuery(null);
            noQueryInput = uriBuilder.toString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        Pattern pathRegex = Pattern.compile(buildParsePattern());

        Matcher matcherFullInput = pathRegex.matcher(input);
        Matcher matcherNoQueryInput = pathRegex.matcher(noQueryInput);

        boolean matches = true;
        Map<String, String> parsedValues = new LinkedHashMap<>();
        if (matcherNoQueryInput.matches()) {
            populateParsedValues(parsedValues, matcherNoQueryInput);
        } else if (matcherFullInput.matches()) {
            populateParsedValues(parsedValues, matcherFullInput);
        } else {
            matches = false;
        }

        return new ParsedUrlTemplate(matches, parsedValues, queryParameters);
    }

    private void populateParsedValues(Map<String, String> parsedValues, Matcher matcher) {
        for (int i = 0; i < parameterNames.size() && i < matcher.groupCount(); i++) {
            String value = matcher.group(i + 1);
            if (value != null) {
                parsedValues.put(parameterNames.get(i), value);
            }
        }
    }
}
