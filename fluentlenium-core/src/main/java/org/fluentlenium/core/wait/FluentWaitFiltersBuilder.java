package org.fluentlenium.core.wait;

import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.FilterType;
import org.fluentlenium.core.filter.matcher.*;

import java.util.regex.Pattern;


public class FluentWaitFiltersBuilder {

    private FluentWaitSelectorMatcher fluentLeniumWait;
    private String attribute;

    public FluentWaitFiltersBuilder(FluentWaitSelectorMatcher fluentWaitBuilder, FilterType filterType) {
        this.fluentLeniumWait = fluentWaitBuilder;
        this.attribute = filterType.name();
    }

    public FluentWaitFiltersBuilder(FluentWaitSelectorMatcher fluentWaitBuilder, String attribute) {
        this.fluentLeniumWait = fluentWaitBuilder;
        this.attribute = attribute;
    }


    public FluentWaitSelectorMatcher equalTo(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new EqualMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitSelectorMatcher contains(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new ContainsMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitSelectorMatcher containsWord(String word) {
        fluentLeniumWait.addFilter(new Filter(attribute, new ContainsWordMatcher(word)));
        return fluentLeniumWait;
    }


    public FluentWaitSelectorMatcher contains(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new ContainsMatcher(equal)));
        return fluentLeniumWait;
    }


    public FluentWaitSelectorMatcher startsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new StartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitSelectorMatcher startsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new StartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitSelectorMatcher endsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new EndsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitSelectorMatcher endsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new EndsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitSelectorMatcher notContains(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotContainsMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitSelectorMatcher notContains(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotContainsMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitSelectorMatcher notStartsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotStartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitSelectorMatcher notStartsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotStartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitSelectorMatcher notEndsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotEndsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitSelectorMatcher notEndsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotEndsWithMatcher(equal)));
        return fluentLeniumWait;
    }


}
