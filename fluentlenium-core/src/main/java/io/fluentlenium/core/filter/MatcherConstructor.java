package io.fluentlenium.core.filter;

import io.fluentlenium.core.filter.matcher.AbstractMatcher;
import io.fluentlenium.core.filter.matcher.ContainsMatcher;
import io.fluentlenium.core.filter.matcher.EndsWithMatcher;
import io.fluentlenium.core.filter.matcher.EqualMatcher;
import io.fluentlenium.core.filter.matcher.NotContainsMatcher;
import io.fluentlenium.core.filter.matcher.NotEndsWithMatcher;
import io.fluentlenium.core.filter.matcher.NotStartsWithMatcher;
import io.fluentlenium.core.filter.matcher.StartsWithMatcher;

import java.util.regex.Pattern;

/**
 * Matcher constructors.
 */
public final class MatcherConstructor {

    private MatcherConstructor() {
        // Utility class
    }

    /**
     * Create a matcher for a containing string
     *
     * @param matcher string matcher
     * @return matcher object
     */
    public static AbstractMatcher contains(String matcher) {
        return new ContainsMatcher(matcher);
    }

    /**
     * Create a matcher for a containing pattern
     *
     * @param pattern pattern object
     * @return matcher object
     */
    public static AbstractMatcher contains(Pattern pattern) {
        return new ContainsMatcher(pattern);
    }

    /**
     * Create a matcher for not containing a string
     *
     * @param matcher string matcher
     * @return matcher object
     */

    public static AbstractMatcher notContains(String matcher) {
        return new NotContainsMatcher(matcher);
    }

    /**
     * Create a matcher for not containing the pattern
     *
     * @param pattern string pattern
     * @return matcher object
     */
    public static AbstractMatcher notContains(Pattern pattern) {
        return new NotContainsMatcher(pattern);
    }

    /**
     * Create a matcher to equal the string matcher
     *
     * @param matcher string matcher
     * @return matcher object
     */
    public static AbstractMatcher equal(String matcher) {
        return new EqualMatcher(matcher);
    }

    /**
     * Create a Pattern given a regex. The regex is compile.
     *
     * @param pattern string pattern
     * @return pattern
     */
    public static Pattern regex(String pattern) {
        return Pattern.compile(pattern);

    }

    /**
     * Create a matcher filtering by a string that start with the matcher
     *
     * @param matcher string matcher
     * @return matcher object
     */
    public static AbstractMatcher startsWith(String matcher) {
        return new StartsWithMatcher(matcher);
    }

    /**
     * Create a matcher filtering by a string that start with the matcher
     *
     * @param pattern pattern
     * @return matcher object
     */
    public static AbstractMatcher startsWith(Pattern pattern) {
        return new StartsWithMatcher(pattern);
    }

    /**
     * Create a matcher filtering by a string that ends with the matcher
     *
     * @param matcher string matcher
     * @return matcher
     */
    public static AbstractMatcher endsWith(String matcher) {
        return new EndsWithMatcher(matcher);
    }

    /**
     * Create a matcher filtering by a string that ends with the pattern
     *
     * @param pattern pattern
     * @return matcher
     */

    public static AbstractMatcher endsWith(Pattern pattern) {
        return new EndsWithMatcher(pattern);
    }

    /**
     * Create a matcher filtering by a string that not starts with the string params
     *
     * @param matcher string matcher
     * @return matcher
     */
    public static AbstractMatcher notStartsWith(String matcher) {
        return new NotStartsWithMatcher(matcher);
    }

    /**
     * Create a matcher filtering by a string that not starts with the pattern params
     *
     * @param pattern pattern
     * @return matcher
     */

    public static AbstractMatcher notStartsWith(Pattern pattern) {
        return new NotStartsWithMatcher(pattern);
    }

    /**
     * Create a matcher filtering by a string that not ends with the string params
     *
     * @param matcher string matcher
     * @return matcher
     */
    public static AbstractMatcher notEndsWith(String matcher) {
        return new NotEndsWithMatcher(matcher);
    }

    /**
     * Create a matcher filtering by a string that not ends with the pattern params
     *
     * @param pattern pattern
     * @return matcher
     */
    public static AbstractMatcher notEndsWith(Pattern pattern) {
        return new NotEndsWithMatcher(pattern);
    }

}
