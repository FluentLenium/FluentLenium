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

    private FluentWaitLocatorSelectorMatcher fluentLeniumWait;
    private String attribute;

    public FluentWaitFiltersBuilder(FluentWaitLocatorSelectorMatcher fluentWaitBuilder, FilterType filterType) {
        this.fluentLeniumWait = fluentWaitBuilder;
        this.attribute = filterType.name();
    }

    public FluentWaitFiltersBuilder(FluentWaitLocatorSelectorMatcher fluentWaitBuilder, String attribute) {
        this.fluentLeniumWait = fluentWaitBuilder;
        this.attribute = attribute;
    }

    public FluentWaitLocatorSelectorMatcher equalTo(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new EqualMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitLocatorSelectorMatcher contains(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new ContainsMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitLocatorSelectorMatcher containsWord(String word) {
        fluentLeniumWait.addFilter(new Filter(attribute, new ContainsWordMatcher(word)));
        return fluentLeniumWait;
    }

    public FluentWaitLocatorSelectorMatcher contains(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new ContainsMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitLocatorSelectorMatcher startsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new StartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitLocatorSelectorMatcher startsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new StartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitLocatorSelectorMatcher endsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new EndsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitLocatorSelectorMatcher endsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new EndsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitLocatorSelectorMatcher notContains(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotContainsMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitLocatorSelectorMatcher notContains(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotContainsMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitLocatorSelectorMatcher notStartsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotStartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitLocatorSelectorMatcher notStartsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotStartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitLocatorSelectorMatcher notEndsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotEndsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitLocatorSelectorMatcher notEndsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotEndsWithMatcher(equal)));
        return fluentLeniumWait;
    }


}
