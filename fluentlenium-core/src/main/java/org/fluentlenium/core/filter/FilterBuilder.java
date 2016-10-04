package org.fluentlenium.core.filter;

import org.fluentlenium.core.filter.matcher.ContainsMatcher;
import org.fluentlenium.core.filter.matcher.ContainsWordMatcher;
import org.fluentlenium.core.filter.matcher.EndsWithMatcher;
import org.fluentlenium.core.filter.matcher.EqualMatcher;
import org.fluentlenium.core.filter.matcher.NotContainsMatcher;
import org.fluentlenium.core.filter.matcher.NotEndsWithMatcher;
import org.fluentlenium.core.filter.matcher.NotStartsWithMatcher;
import org.fluentlenium.core.filter.matcher.StartsWithMatcher;

import java.util.regex.Pattern;

/**
 * Builder for search filters
 */
public class FilterBuilder {

    private final String attribute;

    /**
     * Creates a new filter builder, using custom attributes.
     *
     * @param customAttribute custom attributes to use for filters created by this builder
     */
    public FilterBuilder(final String customAttribute) {
        this.attribute = customAttribute;
    }

    /**
     * Creates a new filter builder, using filter type
     *
     * @param filterType filter type to use for filters created by this builder
     */
    public FilterBuilder(final FilterType filterType) {
        this.attribute = filterType.name();

    }

    /**
     * Builds a filter that match when selection is equal to a given value.
     *
     * @param value value to search
     * @return new filter
     */
    public Filter equalTo(final String value) {
        return new Filter(attribute, new EqualMatcher(value));
    }

    /**
     * Builds a filter that match when selection contains to a given value.
     *
     * @param value value to search
     * @return new filter
     */
    public Filter contains(final String value) {
        return new Filter(attribute, new ContainsMatcher(value));
    }

    /**
     * Builds a filter that match when selection contains a given word.
     *
     * @param word value to search
     * @return new filter
     */
    public Filter containsWord(final String word) {
        return new Filter(attribute, new ContainsWordMatcher(word));
    }

    /**
     * Builds a filter that match when selection contains to a given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public Filter contains(final Pattern pattern) {
        return new Filter(attribute, new ContainsMatcher(pattern));
    }

    /**
     * Builds a filter that match when selection starts with to a given value.
     *
     * @param value value to search
     * @return new filter
     */
    public Filter startsWith(final String value) {
        return new Filter(attribute, new StartsWithMatcher(value));
    }

    /**
     * Builds a filter that match when selection starts with to a given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public Filter startsWith(final Pattern pattern) {
        return new Filter(attribute, new StartsWithMatcher(pattern));
    }

    /**
     * Builds a filter that match when selection ends with to a given value.
     *
     * @param value value to search
     * @return new filter
     */
    public Filter endsWith(final String value) {
        return new Filter(attribute, new EndsWithMatcher(value));
    }

    /**
     * Builds a filter that match when selection ends with to a given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public Filter endsWith(final Pattern pattern) {
        return new Filter(attribute, new EndsWithMatcher(pattern));
    }

    /**
     * Builds a filter that match when selection doesn't contain given value.
     *
     * @param value value to search
     * @return new filter
     */
    public Filter notContains(final String value) {
        return new Filter(attribute, new NotContainsMatcher(value));
    }

    /**
     * Builds a filter that match when selection doesn't contain given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public Filter notContains(final Pattern pattern) {
        return new Filter(attribute, new NotContainsMatcher(pattern));
    }

    /**
     * Builds a filter that match when selection doesn't start with given value.
     *
     * @param value value to search
     * @return new filter
     */
    public Filter notStartsWith(final String value) {
        return new Filter(attribute, new NotStartsWithMatcher(value));
    }

    /**
     * Builds a filter that match when selection doesn't start with given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public Filter notStartsWith(final Pattern pattern) {
        return new Filter(attribute, new NotStartsWithMatcher(pattern));
    }

    /**
     * Builds a filter that match when selection doesn't end with given value.
     *
     * @param value value to search
     * @return new filter
     */
    public Filter notEndsWith(final String value) {
        return new Filter(attribute, new NotEndsWithMatcher(value));
    }

    /**
     * Builds a filter that match when selection doesn't end with given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public Filter notEndsWith(final Pattern pattern) {
        return new Filter(attribute, new NotEndsWithMatcher(pattern));
    }

}
