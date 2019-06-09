package org.fluentlenium.assertj.custom;

import org.assertj.core.api.ListAssert;

/**
 * Assertion interface for attribute validation within a list of attribute values.
 */
public interface ListAttributeAssert extends AttributeAssert<ListAssert<String>> {

    /**
     * Checks if at least one of the elements in the list of elements has the given property.
     * <p>
     * It allows users to do not just presence validation but apply chained List assertions
     * to further validate the attribute values.
     *
     * @param attribute the attribute to find
     * @return a new {@code ListAssert} object with the actual attribute values
     */
    ListAssert<String> hasAttribute(String attribute);
}
