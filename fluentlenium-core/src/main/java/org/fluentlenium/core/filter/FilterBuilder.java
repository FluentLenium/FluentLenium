package org.fluentlenium.core.filter;

import org.fluentlenium.core.filter.matcher.*;

import java.util.regex.Pattern;


public class FilterBuilder {

    private String attribute;

    public FilterBuilder(String customAttribute) {
        this.attribute = customAttribute;
    }

    public FilterBuilder(FilterType filterType) {
        this.attribute = filterType.name();

    }

    public Filter equalTo(String equal) {
        return new Filter(attribute, new EqualMatcher(equal));
    }

    public Filter contains(String equal) {
        return new Filter(attribute, new ContainsMatcher(equal));
    }

    public Filter containsWord(String equal) {
        return new Filter(attribute, new ContainsWordMatcher(equal));
    }

    public Filter contains(Pattern equal) {
        return new Filter(attribute, new ContainsMatcher(equal));
    }

    public Filter startsWith(String equal) {
        return new Filter(attribute, new StartsWithMatcher(equal));
    }

    public Filter startsWith(Pattern equal) {
        return new Filter(attribute, new StartsWithMatcher(equal));
    }

    public Filter endsWith(String equal) {
        return new Filter(attribute, new EndsWithMatcher(equal));
    }

    public Filter endsWith(Pattern equal) {
        return new Filter(attribute, new EndsWithMatcher(equal));
    }

    public Filter notContains(String equal) {
        return new Filter(attribute, new NotContainsMatcher(equal));
    }

    public Filter notContains(Pattern equal) {
        return new Filter(attribute, new NotContainsMatcher(equal));
    }

    public Filter notStartsWith(String equal) {
        return new Filter(attribute, new NotStartsWithMatcher(equal));
    }

    public Filter notStartsWith(Pattern equal) {
        return new Filter(attribute, new NotStartsWithMatcher(equal));
    }

    public Filter notEndsWith(String equal) {
        return new Filter(attribute, new NotEndsWithMatcher(equal));
    }

    public Filter notEndsWith(Pattern equal) {
        return new Filter(attribute, new NotEndsWithMatcher(equal));
    }


}
