package org.fluentlenium.assertj.custom;

import org.assertj.core.api.ListAssert;

/**
 * Assertion interface for attribute validation within a list of attribute values.
 */
public interface ListAttributeAssert extends AttributeAssert<ListAssert<String>> {

    /**
     * Checks if the list of elements all have the given property.
     * <p>
     * It allows users to do not just presence validation but apply chained String assertions
     * to further validate the attribute value.
     * <p>
     * Examples:
     * <p>
     * Validating the presence of an attribute on all elements of a {@link org.fluentlenium.core.domain.FluentList}:
     * <pre>
     * assertThat(elementList).hasAttribute("href");
     * </pre>
     * <p>
     * Validating both the presence of an attribute on all elements of a list, and the value of all of those attributes:
     * <pre>
     * assertThat(elementList).hasAttribute("href").matches("^https.*$");
     * </pre>
     *
     * @param attribute the attribute to find
     * @return a new {@code ListAssert} object with the actual attribute values
     */
    ListAssert<String> hasAttribute(String attribute);
}
