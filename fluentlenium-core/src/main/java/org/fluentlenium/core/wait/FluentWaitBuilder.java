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

public class FluentWaitBuilder {

    private FluentWaitMatcher fluentLeniumWait;
    private String attribute;

    public FluentWaitBuilder(FluentWaitMatcher fluentWaitBuilder, FilterType filterType) {
        this.fluentLeniumWait = fluentWaitBuilder;
        this.attribute = filterType.name();

    }

    public FluentWaitBuilder(FluentWaitMatcher fluentWaitBuilder, String attribute) {
        this.fluentLeniumWait = fluentWaitBuilder;
        this.attribute = attribute;

    }

    public FluentWaitMatcher equalTo(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new EqualMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitMatcher contains(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new ContainsMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitMatcher containsWord(String word) {
        fluentLeniumWait.addFilter(new Filter(attribute, new ContainsWordMatcher(word)));
        return fluentLeniumWait;
    }

    public FluentWaitMatcher contains(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new ContainsMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitMatcher startsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new StartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitMatcher startsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new StartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitMatcher endsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new EndsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitMatcher endsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new EndsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitMatcher notContains(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotContainsMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitMatcher notContains(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotContainsMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitMatcher notStartsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotStartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitMatcher notStartsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotStartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitMatcher notEndsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotEndsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitMatcher notEndsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotEndsWithMatcher(equal)));
        return fluentLeniumWait;
    }
}
