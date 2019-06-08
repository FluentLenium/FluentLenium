package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractStringAssert;

/**
 * Assertion interface for element attribute validation.
 */
public interface ElementAttributeAssert extends AttributeAssert<AbstractStringAssert> {

    /**
     * Checks if the element has the given property.
     * <p>
     * It allows users to do not just presence validation but apply chained String assertions
     * to further validate the attribute value.
     * <p>
     * Examples:
     * <p>
     * Validating the presence of an attribute on an element:
     * <pre>
     * assertThat(element).hasAttribute("href");
     * </pre>
     * <p>
     * Validating both the presence of an attribute on an element, and the value of that attribute:
     * <pre>
     * assertThat(element).hasAttribute("href").isEqualTo("https://duckduckgo.com");
     * </pre>
     *
     * @param attribute the attribute to find
     * @return a new {@code AbstractStringAssert} object with the actual attribute value
     */
    AbstractStringAssert hasAttribute(String attribute);
}
