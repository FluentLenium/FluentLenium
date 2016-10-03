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

public class FilterBuilder {

    private final String attribute;

    public FilterBuilder(final String customAttribute) {
        this.attribute = customAttribute;
    }

    public FilterBuilder(final FilterType filterType) {
        this.attribute = filterType.name();

    }

    public Filter equalTo(final String equal) {
        return new Filter(attribute, new EqualMatcher(equal));
    }

    public Filter contains(final String equal) {
        return new Filter(attribute, new ContainsMatcher(equal));
    }

    public Filter containsWord(final String equal) {
        return new Filter(attribute, new ContainsWordMatcher(equal));
    }

    public Filter contains(final Pattern equal) {
        return new Filter(attribute, new ContainsMatcher(equal));
    }

    public Filter startsWith(final String equal) {
        return new Filter(attribute, new StartsWithMatcher(equal));
    }

    public Filter startsWith(final Pattern equal) {
        return new Filter(attribute, new StartsWithMatcher(equal));
    }

    public Filter endsWith(final String equal) {
        return new Filter(attribute, new EndsWithMatcher(equal));
    }

    public Filter endsWith(final Pattern equal) {
        return new Filter(attribute, new EndsWithMatcher(equal));
    }

    public Filter notContains(final String equal) {
        return new Filter(attribute, new NotContainsMatcher(equal));
    }

    public Filter notContains(final Pattern equal) {
        return new Filter(attribute, new NotContainsMatcher(equal));
    }

    public Filter notStartsWith(final String equal) {
        return new Filter(attribute, new NotStartsWithMatcher(equal));
    }

    public Filter notStartsWith(final Pattern equal) {
        return new Filter(attribute, new NotStartsWithMatcher(equal));
    }

    public Filter notEndsWith(final String equal) {
        return new Filter(attribute, new NotEndsWithMatcher(equal));
    }

    public Filter notEndsWith(final Pattern equal) {
        return new Filter(attribute, new NotEndsWithMatcher(equal));
    }

}
