package org.fluentlenium.core.wait;

import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.FilterType;
import org.fluentlenium.core.filter.matcher.ContainsMatcher;
import org.fluentlenium.core.filter.matcher.ContainsWordMatcher;
import org.fluentlenium.core.filter.matcher.EndsWithMatcher;
import org.fluentlenium.core.filter.matcher.EqualMatcher;
import org.fluentlenium.core.filter.matcher.NotContainsMatcher;
import org.fluentlenium.core.filter.matcher.NotEndsWithMatcher;
import org.fluentlenium.core.filter.matcher.NotStartsWithMatcher;
import org.fluentlenium.core.filter.matcher.StartsWithMatcher;

import java.util.regex.Pattern;

public class FluentWaitFiltersBuilder {

    private FluentWaitLocatorSelectorMatcher matcher;
    private String attribute;

    public FluentWaitFiltersBuilder(FluentWaitLocatorSelectorMatcher fluentWaitBuilder, FilterType filterType) {
        this.matcher = fluentWaitBuilder;
        this.attribute = filterType.name();
    }

    public FluentWaitFiltersBuilder(FluentWaitLocatorSelectorMatcher matcher, String attribute) {
        this.matcher = matcher;
        this.attribute = attribute;
    }

    public FluentWaitLocatorSelectorMatcher equalTo(String equal) {
        matcher.addFilter(new Filter(attribute, new EqualMatcher(equal)));
        return matcher;
    }

    public FluentWaitLocatorSelectorMatcher contains(String equal) {
        matcher.addFilter(new Filter(attribute, new ContainsMatcher(equal)));
        return matcher;
    }

    public FluentWaitLocatorSelectorMatcher containsWord(String word) {
        matcher.addFilter(new Filter(attribute, new ContainsWordMatcher(word)));
        return matcher;
    }

    public FluentWaitLocatorSelectorMatcher contains(Pattern equal) {
        matcher.addFilter(new Filter(attribute, new ContainsMatcher(equal)));
        return matcher;
    }

    public FluentWaitLocatorSelectorMatcher startsWith(String equal) {
        matcher.addFilter(new Filter(attribute, new StartsWithMatcher(equal)));
        return matcher;
    }

    public FluentWaitLocatorSelectorMatcher startsWith(Pattern equal) {
        matcher.addFilter(new Filter(attribute, new StartsWithMatcher(equal)));
        return matcher;
    }

    public FluentWaitLocatorSelectorMatcher endsWith(String equal) {
        matcher.addFilter(new Filter(attribute, new EndsWithMatcher(equal)));
        return matcher;
    }

    public FluentWaitLocatorSelectorMatcher endsWith(Pattern equal) {
        matcher.addFilter(new Filter(attribute, new EndsWithMatcher(equal)));
        return matcher;
    }

    public FluentWaitLocatorSelectorMatcher notContains(String equal) {
        matcher.addFilter(new Filter(attribute, new NotContainsMatcher(equal)));
        return matcher;
    }

    public FluentWaitLocatorSelectorMatcher notContains(Pattern equal) {
        matcher.addFilter(new Filter(attribute, new NotContainsMatcher(equal)));
        return matcher;
    }

    public FluentWaitLocatorSelectorMatcher notStartsWith(String equal) {
        matcher.addFilter(new Filter(attribute, new NotStartsWithMatcher(equal)));
        return matcher;
    }

    public FluentWaitLocatorSelectorMatcher notStartsWith(Pattern equal) {
        matcher.addFilter(new Filter(attribute, new NotStartsWithMatcher(equal)));
        return matcher;
    }

    public FluentWaitLocatorSelectorMatcher notEndsWith(String equal) {
        matcher.addFilter(new Filter(attribute, new NotEndsWithMatcher(equal)));
        return matcher;
    }

    public FluentWaitLocatorSelectorMatcher notEndsWith(Pattern equal) {
        matcher.addFilter(new Filter(attribute, new NotEndsWithMatcher(equal)));
        return matcher;
    }


}
