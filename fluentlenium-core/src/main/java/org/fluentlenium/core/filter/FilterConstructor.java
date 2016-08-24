package org.fluentlenium.core.filter;

import org.fluentlenium.core.filter.matcher.ContainsWordMatcher;
import org.fluentlenium.core.filter.matcher.Matcher;


public final class FilterConstructor {

    private FilterConstructor() {
    }

    /**
     * Create a filter by name
     *
     * @param name        element name
     * @param type        filter type
     * @param matcherType class for matcherType
     * @return filter object
     */
    public static Filter buildFilter(String name, FilterType type, Class matcherType) {
        if (matcherType.equals(ContainsWordMatcher.class)) {
            return new Filter(FilterType.NAME, name);
        }
        return null;
    }

    /**
     * Create a filter by name
     *
     * @param name element name
     * @return filter object
     */
    public static Filter withName(String name) {
        return new Filter(FilterType.NAME, name);
    }

    /**
     * Create a filter by id
     *
     * @param id element id
     * @return filter object
     */
    public static Filter withId(String id) {
        return new Filter(FilterType.ID, id);
    }

    /**
     * Create a filter by class
     *
     * @param klass CSS class name
     * @return filter object
     */
    public static Filter withClass(String klass) {
        return new Filter(FilterType.CLASS, klass);
    }

    /**
     * Create a filter by text
     *
     * @param text to filter in content
     * @return filter object
     */
    public static Filter withText(String text) {
        return new Filter(FilterType.TEXT, MatcherConstructor.equal(text));
    }

    /**
     * Create a filter by text
     *
     * @param text to filter in content
     * @return filter object
     */
    public static Filter containingText(String text) {
        return new Filter(FilterType.TEXT, MatcherConstructor.contains(text));
    }

    /**
     * Create a filter builder for the attribute
     *
     * @param attribute attribute name
     * @return filter builder object
     */
    public static FilterBuilder with(String attribute) {
        return new FilterBuilder(attribute);
    }

    /**
     * Create a filter builder for the attribute by name
     *
     * @return filter builder object
     */
    public static FilterBuilder withName() {
        return new FilterBuilder(FilterType.NAME);
    }

    /**
     * Create a filter builder for the attribute by id
     *
     * @return filter builder object
     */
    public static FilterBuilder withId() {
        return new FilterBuilder(FilterType.ID);
    }

    /**
     * Create a filter builder for the attribute by class
     *
     * @return filter builder object
     */
    public static FilterBuilder withClass() {
        return new FilterBuilder(FilterType.CLASS);
    }

    /**
     * Create a filter builder for the attribute by text
     *
     * @return filter builder object
     */
    public static FilterBuilder withText() {
        return new FilterBuilder(FilterType.TEXT);
    }

}
