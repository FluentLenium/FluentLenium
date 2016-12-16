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
    public FilterBuilder(String customAttribute) {
        attribute = customAttribute;
    }

    /**
     * Builds a filter that match when selection is equal to a given value.
     *
     * @param value value to search
     * @return new filter
     */
    public AttributeFilter equalTo(String value) {
        return new AttributeFilter(attribute, new EqualMatcher(value));
    }

    /**
     * Builds a filter that match when selection contains to a given value.
     *
     * @param value value to search
     * @return new filter
     */
    public AttributeFilter contains(String value) {
        return new AttributeFilter(attribute, new ContainsMatcher(value));
    }

    /**
     * Builds a filter that match when selection contains a given word.
     *
     * @param word value to search
     * @return new filter
     */
    public AttributeFilter containsWord(String word) {
        return new AttributeFilter(attribute, new ContainsWordMatcher(word));
    }

    /**
     * Builds a filter that match when selection contains to a given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public AttributeFilter contains(Pattern pattern) {
        return new AttributeFilter(attribute, new ContainsMatcher(pattern));
    }

    /**
     * Builds a filter that match when selection starts with to a given value.
     *
     * @param value value to search
     * @return new filter
     */
    public AttributeFilter startsWith(String value) {
        return new AttributeFilter(attribute, new StartsWithMatcher(value));
    }

    /**
     * Builds a filter that match when selection starts with to a given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public AttributeFilter startsWith(Pattern pattern) {
        return new AttributeFilter(attribute, new StartsWithMatcher(pattern));
    }

    /**
     * Builds a filter that match when selection ends with to a given value.
     *
     * @param value value to search
     * @return new filter
     */
    public AttributeFilter endsWith(String value) {
        return new AttributeFilter(attribute, new EndsWithMatcher(value));
    }

    /**
     * Builds a filter that match when selection ends with to a given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public AttributeFilter endsWith(Pattern pattern) {
        return new AttributeFilter(attribute, new EndsWithMatcher(pattern));
    }

    /**
     * Builds a filter that match when selection doesn't contain given value.
     *
     * @param value value to search
     * @return new filter
     */
    public AttributeFilter notContains(String value) {
        return new AttributeFilter(attribute, new NotContainsMatcher(value));
    }

    /**
     * Builds a filter that match when selection doesn't contain given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public AttributeFilter notContains(Pattern pattern) {
        return new AttributeFilter(attribute, new NotContainsMatcher(pattern));
    }

    /**
     * Builds a filter that match when selection doesn't start with given value.
     *
     * @param value value to search
     * @return new filter
     */
    public AttributeFilter notStartsWith(String value) {
        return new AttributeFilter(attribute, new NotStartsWithMatcher(value));
    }

    /**
     * Builds a filter that match when selection doesn't start with given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public AttributeFilter notStartsWith(Pattern pattern) {
        return new AttributeFilter(attribute, new NotStartsWithMatcher(pattern));
    }

    /**
     * Builds a filter that match when selection doesn't end with given value.
     *
     * @param value value to search
     * @return new filter
     */
    public AttributeFilter notEndsWith(String value) {
        return new AttributeFilter(attribute, new NotEndsWithMatcher(value));
    }

    /**
     * Builds a filter that match when selection doesn't end with given pattern.
     *
     * @param pattern pattern to match
     * @return new filter
     */
    public AttributeFilter notEndsWith(Pattern pattern) {
        return new AttributeFilter(attribute, new NotEndsWithMatcher(pattern));
    }

}
