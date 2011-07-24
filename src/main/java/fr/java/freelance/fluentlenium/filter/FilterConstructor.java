package fr.java.freelance.fluentlenium.filter;

import fr.java.freelance.fluentlenium.filter.*;
import fr.java.freelance.fluentlenium.filter.matcher.ContainsMatcher;
import fr.java.freelance.fluentlenium.filter.matcher.EqualMatcher;
import fr.java.freelance.fluentlenium.filter.matcher.Matcher;
import fr.java.freelance.fluentlenium.filter.matcher.NotContainsMatcher;

import java.util.regex.Pattern;


public class FilterConstructor {

    private FilterConstructor() {
    }

    /**
     * Create a filter by name
     *
     * @param name
     * @return
     */
    public static Filter withName(String name) {
        return new Filter(FilterType.NAME, name);
    }

    /**
     * Create a filter by id
     *
     * @param id
     * @return
     */
    public static Filter withId(String id) {
        return new Filter(FilterType.ID, id);
    }

    /**
     * Create a filter by text
     *
     * @param text
     * @return
     */
    public static Filter withText(String text) {
        return new Filter(FilterType.TEXT, equal(text));
    }

    /**
     * Create a filter by name with matcher
     *
     * @param matcher
     * @return
     */
    public static Filter withName(Matcher matcher) {

        return new Filter(FilterType.NAME, matcher);
    }

    public static Matcher contains(String matcher) {
        return new ContainsMatcher(matcher);
    }

    public static Matcher contains(Pattern pattern) {
        return new ContainsMatcher(pattern);
    }

    public static Matcher notContains(String matcher) {
        return new NotContainsMatcher(matcher);
    }

    public static Matcher notContains(Pattern pattern) {
        return new NotContainsMatcher(pattern);
    }

    public static Matcher equal(String matcher) {
        return new EqualMatcher(matcher);
    }

    public static Pattern regex(String pattern) {
        return Pattern.compile(pattern);

    }

    /* public static Matcher notEqual(String matcher) {
        return new NotEquaMatcherl(matcher);
    }

    public static Matcher startsWith(String matcher) {
        return new BeginWithMatcher(matcher);
    }

    public static Matcher notStartsWith(String matcher) {
        return new NotBeginWithMatcher(matcher);
    }

    public static Matcher endsWith(String matcher) {
        return new EndsWithMatcher(matcher);
    } public static Matcher notEndsWith(String matcher) {
        return new NotEndsWith(matcher);
    }
*/


    /**
     * Create a filter by id
     *
     * @param matcher
     * @return
     */
    public static Filter withId(Matcher matcher) {
        return new Filter(FilterType.ID, matcher);
    }

    /**
     * Create a filter by text
     *
     * @param matcher
     * @return
     */
    public static Filter withText(Matcher matcher) {
        return new Filter(FilterType.TEXT, matcher);
    }
}
