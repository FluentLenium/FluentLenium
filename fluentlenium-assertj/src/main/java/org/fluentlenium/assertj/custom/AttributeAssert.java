package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;

/**
 * Base assertion interface for attribute validation within a single attribute value or a list of attribute values.
 *
 * @param <T> the type of AssertJ assert to return
 */
public interface AttributeAssert<T> {

    /**
     * Checks if the element, or at least one element in a list of elements, has the given property.
     * <p>
     * It allows users to do not just presence validation but apply chained List assertions
     * to further validate the attribute values.
     *
     * @param attribute the attribute to find
     * @return a new assert object with the actual attribute value
     */
    T hasAttribute(String attribute);

    /**
     * Checks if the element doesn't have, or no element in a list of elements has, the given property.
     *
     * @param attribute attribute to find the absence of
     * @return {@code this} assertion object.
     */
    AbstractAssert hasNotAttribute(String attribute);
}
