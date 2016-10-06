package org.fluentlenium.core.filter;

import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Filter constructors.
 */
public final class FilterConstructor {

    private FilterConstructor() {
        // Utility class
    }

    /**
     * Create a filter by name
     *
     * @param name element name
     * @return filter object
     */
    public static AttributeFilter withName(final String name) {
        return new AttributeFilter("name", name);
    }

    /**
     * Create a filter by id
     *
     * @param id element id
     * @return filter object
     */
    public static AttributeFilter withId(final String id) {
        return new AttributeFilter("id", id);
    }

    /**
     * Create a filter by class
     *
     * @param klass CSS class name
     * @return filter object
     */
    public static AttributeFilter withClass(final String klass) {
        return new AttributeFilter("class", klass);
    }

    /**
     * Create a filter by text
     *
     * @param text to filter in content
     * @return filter object
     */
    public static AttributeFilter withText(final String text) {
        return new AttributeFilter("text", MatcherConstructor.equal(text));
    }

    /**
     * Create a filter by text
     *
     * @param text to filter in content
     * @return filter object
     */
    public static AttributeFilter containingText(final String text) {
        return new AttributeFilter("text", MatcherConstructor.contains(text));
    }

    /**
     * Create a filter builder for the attribute by text
     *
     * @return filter builder object
     */
    public static FilterBuilder withText() {
        return new FilterBuilder("text");
    }

    /**
     * Create a filter by text content
     *
     * @param text to filter in content
     * @return filter object
     */
    public static AttributeFilter withTextContent(final String text) {
        return new AttributeFilter("textContent", MatcherConstructor.equal(text));
    }

    /**
     * Create a filter by text content
     *
     * @param text to filter in content
     * @return filter object
     */
    public static AttributeFilter containingTextContent(final String text) {
        return new AttributeFilter("textContent", MatcherConstructor.contains(text));
    }

    /**
     * Create a filter builder by text content
     *
     * @return filter builder object
     */
    public static FilterBuilder withTextContent() {
        return new FilterBuilder("textContent");
    }

    /**
     * Create a filter builder for the attribute
     *
     * @param attribute attribute name
     * @return filter builder object
     */
    public static FilterBuilder with(final String attribute) {
        return new FilterBuilder(attribute);
    }

    /**
     * Create a filter builder for the attribute by name
     *
     * @return filter builder object
     */
    public static FilterBuilder withName() {
        return new FilterBuilder("name");
    }

    /**
     * Create a filter builder for the attribute by id
     *
     * @return filter builder object
     */
    public static FilterBuilder withId() {
        return new FilterBuilder("id");
    }

    /**
     * Create a filter builder for the attribute by class
     *
     * @return filter builder object
     */
    public static FilterBuilder withClass() {
        return new FilterBuilder("class");
    }

    /**
     * Create a filter based on a element predicate
     *
     * @param predicate predicate
     * @return predicate filter
     */
    public static PredicateFilter withPredicate(Predicate<FluentWebElement> predicate) {
        return new PredicateFilter(predicate);
    }

}
