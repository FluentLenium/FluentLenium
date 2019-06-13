package org.fluentlenium.assertj.custom;

import org.assertj.core.api.ListAssert;

/**
 * Assertion interface for attribute validation within a list of elements.
 */
public interface ListAttributeAssert extends AttributeAssert<ListAssert<String>> {

    /**
     * Checks if at least one of the elements in the list of elements has the given property.
     * <p>
     * It allows users to do not just presence validation but to apply chained List assertions
     * to further validate the attribute values.
     * <p>
     * Examples:
     * <p>
     * Validating the presence of an attribute on a {@link org.fluentlenium.core.domain.FluentList}:
     * <pre>
     * assertThat(fluentList).hasAttribute("class");
     * </pre>
     * <p>
     * Validating both the presence of an attribute on a fluent list, and the value of that attribute:
     * <pre>
     * assertThat(element).hasAttribute("class").containsAnyOf("some-class", "other-class");
     * </pre>
     *
     * @param attribute the attribute to find
     * @return a new {@code ListAssert} object with the actual attribute values
     */
    ListAssert<String> hasAttribute(String attribute);
}
