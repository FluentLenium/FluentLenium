package org.fluentlenium.core.filter;

import org.fluentlenium.core.filter.matcher.AbstractMacher;
import org.fluentlenium.core.filter.matcher.ContainsMatcher;
import org.fluentlenium.core.filter.matcher.EndsWithMatcher;
import org.fluentlenium.core.filter.matcher.EqualMatcher;
import org.fluentlenium.core.filter.matcher.NotContainsMatcher;
import org.fluentlenium.core.filter.matcher.NotEndsWithMatcher;
import org.fluentlenium.core.filter.matcher.NotStartsWithMatcher;
import org.fluentlenium.core.filter.matcher.StartsWithMatcher;

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
    public static AbstractMacher contains(final String matcher) {
        return new ContainsMatcher(matcher);
    }

    /**
     * Create a matcher for a containing pattern
     *
     * @param pattern pattern object
     * @return matcher object
     */
    public static AbstractMacher contains(final Pattern pattern) {
        return new ContainsMatcher(pattern);
    }

    /**
     * Create a matcher for not containing a string
     *
     * @param matcher string matcher
     * @return matcher object
     */

    public static AbstractMacher notContains(final String matcher) {
        return new NotContainsMatcher(matcher);
    }

    /**
     * Create a matcher for not containing the pattern
     *
     * @param pattern string pattern
     * @return matcher object
     */
    public static AbstractMacher notContains(final Pattern pattern) {
        return new NotContainsMatcher(pattern);
    }

    /**
     * Create a matcher to equal the string matcher
     *
     * @param matcher string matcher
     * @return matcher object
     */
    public static AbstractMacher equal(final String matcher) {
        return new EqualMatcher(matcher);
    }

    /**
     * Create a Pattern given a regex. The regex is compile.
     *
     * @param pattern string pattern
     * @return pattern
     */
    public static Pattern regex(final String pattern) {
        return Pattern.compile(pattern);

    }

    /**
     * Create a matcher filtering by a string that start with the matcher
     *
     * @param matcher string matcher
     * @return matcher object
     */
    public static AbstractMacher startsWith(final String matcher) {
        return new StartsWithMatcher(matcher);
    }

    /**
     * Create a matcher filtering by a string that start with the matcher
     *
     * @param pattern pattern
     * @return matcher object
     */
    public static AbstractMacher startsWith(final Pattern pattern) {
        return new StartsWithMatcher(pattern);
    }

    /**
     * Create a matcher filtering by a string that ends with the matcher
     *
     * @param matcher string matcher
     * @return matcher
     */
    public static AbstractMacher endsWith(final String matcher) {
        return new EndsWithMatcher(matcher);
    }

    /**
     * Create a matcher filtering by a string that ends with the pattern
     *
     * @param pattern pattern
     * @return matcher
     */

    public static AbstractMacher endsWith(final Pattern pattern) {
        return new EndsWithMatcher(pattern);
    }

    /**
     * Create a matcher filtering by a string that not starts with the string params
     *
     * @param matcher string matcher
     * @return matcher
     */
    public static AbstractMacher notStartsWith(final String matcher) {
        return new NotStartsWithMatcher(matcher);
    }

    /**
     * Create a matcher filtering by a string that not starts with the pattern params
     *
     * @param pattern pattern
     * @return matcher
     */

    public static AbstractMacher notStartsWith(final Pattern pattern) {
        return new NotStartsWithMatcher(pattern);
    }

    /**
     * Create a matcher filtering by a string that not ends with the string params
     *
     * @param matcher string matcher
     * @return matcher
     */
    public static AbstractMacher notEndsWith(final String matcher) {
        return new NotEndsWithMatcher(matcher);
    }

    /**
     * Create a matcher filtering by a string that not ends with the pattern params
     *
     * @param pattern pattern
     * @return matcher
     */
    public static AbstractMacher notEndsWith(final Pattern pattern) {
        return new NotEndsWithMatcher(pattern);
    }

}
