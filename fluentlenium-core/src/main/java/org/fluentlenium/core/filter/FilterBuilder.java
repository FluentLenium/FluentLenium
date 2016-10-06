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
     * Builds a filter that match when selection is equal to a given value.
     *
     * @param value value to search
     * @return new filter
     */
    public AttributeFilter equalTo(final String value) {
        return new AttributeFilter(attribute, new EqualMatcher(value));
    }

    /**
     * Builds a filter that match when selection contains to a given value.
     *
     * @param value value to search
     * @return new filter
     */
    public AttributeFilter contains(final String value) {
        return new AttributeFilter(attribute, new ContainsMatcher(value));
    }

    /**
     * Builds a filter that match when selection contains a given word.
     *
     * @param word value to search
     * @return new filter
     */
    public AttributeFilter containsWord(final String word) {
        return new AttributeFilter(attribute, new ContainsWordMatcher(word));
    }

    /**
     * Builds a filter that match when selection contains to a given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public AttributeFilter contains(final Pattern pattern) {
        return new AttributeFilter(attribute, new ContainsMatcher(pattern));
    }

    /**
     * Builds a filter that match when selection starts with to a given value.
     *
     * @param value value to search
     * @return new filter
     */
    public AttributeFilter startsWith(final String value) {
        return new AttributeFilter(attribute, new StartsWithMatcher(value));
    }

    /**
     * Builds a filter that match when selection starts with to a given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public AttributeFilter startsWith(final Pattern pattern) {
        return new AttributeFilter(attribute, new StartsWithMatcher(pattern));
    }

    /**
     * Builds a filter that match when selection ends with to a given value.
     *
     * @param value value to search
     * @return new filter
     */
    public AttributeFilter endsWith(final String value) {
        return new AttributeFilter(attribute, new EndsWithMatcher(value));
    }

    /**
     * Builds a filter that match when selection ends with to a given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public AttributeFilter endsWith(final Pattern pattern) {
        return new AttributeFilter(attribute, new EndsWithMatcher(pattern));
    }

    /**
     * Builds a filter that match when selection doesn't contain given value.
     *
     * @param value value to search
     * @return new filter
     */
    public AttributeFilter notContains(final String value) {
        return new AttributeFilter(attribute, new NotContainsMatcher(value));
    }

    /**
     * Builds a filter that match when selection doesn't contain given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public AttributeFilter notContains(final Pattern pattern) {
        return new AttributeFilter(attribute, new NotContainsMatcher(pattern));
    }

    /**
     * Builds a filter that match when selection doesn't start with given value.
     *
     * @param value value to search
     * @return new filter
     */
    public AttributeFilter notStartsWith(final String value) {
        return new AttributeFilter(attribute, new NotStartsWithMatcher(value));
    }

    /**
     * Builds a filter that match when selection doesn't start with given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public AttributeFilter notStartsWith(final Pattern pattern) {
        return new AttributeFilter(attribute, new NotStartsWithMatcher(pattern));
    }

    /**
     * Builds a filter that match when selection doesn't end with given value.
     *
     * @param value value to search
     * @return new filter
     */
    public AttributeFilter notEndsWith(final String value) {
        return new AttributeFilter(attribute, new NotEndsWithMatcher(value));
    }

    /**
     * Builds a filter that match when selection doesn't end with given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public AttributeFilter notEndsWith(final Pattern pattern) {
        return new AttributeFilter(attribute, new NotEndsWithMatcher(pattern));
    }

}
