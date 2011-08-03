package fr.javafreelance.fluentlenium.filter;

import fr.javafreelance.fluentlenium.filter.matcher.*;

import java.util.regex.Pattern;


public class FilterConstructor {

    private FilterConstructor() {
    }

    /**
     * Create a integration by name
     *
     * @param name
     * @return
     */
    public static Filter withName(String name) {
        return new Filter(FilterType.NAME, name);
    }

    /**
     * Create a integration by id
     *
     * @param id
     * @return
     */
    public static Filter withId(String id) {
        return new Filter(FilterType.ID, id);
    }

    /**
     * Create a integration by a customattribute
     *
     * @param customAttribute
     * @param value
     * @return
     */
    public static Filter with(String customAttribute, String value) {
        return new Filter(FilterType.CUSTOM, customAttribute, value);
    }

    /**
     * Create a integration by text
     *
     * @param text
     * @return
     */
    public static Filter withText(String text) {
        return new Filter(FilterType.TEXT, equal(text));
    }

    /**
     * Create a integration by name with matcher
     *
     * @param matcher
     * @return
     */
    public static Filter withName(Matcher matcher) {
        return new Filter(FilterType.NAME, matcher);
    }

    /**
     * Create a integration by id
     *
     * @param matcher
     * @return
     */
    public static Filter withId(Matcher matcher) {
        return new Filter(FilterType.ID, matcher);
    }

    /**
     * Create a integration by text
     *
     * @param matcher
     * @return
     */
    public static Filter withText(Matcher matcher) {
        return new Filter(FilterType.TEXT, matcher);
    }


    /**
     * Create a integration by a customattribute
     *
     * @param customAttribute
     * @param matcher
     * @return
     */
    public static Filter with(String customAttribute, Matcher matcher) {
        return new Filter(FilterType.CUSTOM, customAttribute, matcher);
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

    public static Matcher startsWith(String matcher) {
        return new StartsWithMatcher(matcher);
    }

    public static Matcher startsWith(Pattern pattern) {
        return new StartsWithMatcher(pattern);
    }

    public static Matcher endsWith(String matcher) {
        return new EndsWithMatcher(matcher);
    }

    public static Matcher endsWith(Pattern pattern) {
        return new EndsWithMatcher(pattern);
    }

    public static Matcher notStartsWith(String matcher) {
        return new StartsWithMatcher(matcher);
    }

    public static Matcher notStartsWith(Pattern pattern) {
        return new StartsWithMatcher(pattern);
    }

    public static Matcher notEndsWith(String matcher) {
        return new EndsWithMatcher(matcher);
    }

    public static Matcher notEndsWith(Pattern pattern) {
        return new EndsWithMatcher(pattern);
    }
    /* public static Matcher notEqual(String matcher) {
        return new NotEquaMatcherl(matcher);
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


}
