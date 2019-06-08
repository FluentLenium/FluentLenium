package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;

/**
 * Base assertion interface for attribute validation within a single attribute value or a list of attribute values.
 *
 * @param <T> the type of AssertJ assert to return
 */
public interface AttributeAssert<T> {

    /**
     * Checks if the list of elements all have the given property.
     *
     * @param attribute the attribute to find
     * @return a new assert object with the actual attribute value
     */
    T hasAttribute(String attribute);

    /**
     * Checks if the element or among a list of elements none has the given property.
     *
     * @param attribute attribute to find the absence of
     * @return {@code this} assertion object.
     */
    AbstractAssert hasNotAttribute(String attribute);
}
