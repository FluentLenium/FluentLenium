package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.Dimension;

/**
 * Base assertion interface.
 */
public interface FluentAssert {

    /**
     * Checks if the element, or at least one element in a list of elements, contain the text.
     *
     * @param textToFind text to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasText(String textToFind);

    /**
     * Checks if the element, or at least one element in a list of elements, matches the given regex
     *
     * @param regexToBeMatched regex to be matched
     * @return {@code this} assertion object.
     */
    AbstractAssert hasTextMatching(String regexToBeMatched);

    /**
     * Checks if the element does not contain, or none of the elements in a list of elements contain the text
     *
     * @param textToFind text to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasNotText(String textToFind);

    /**
     * Checks if the element, or at least one element in a list of elements, has the given id
     *
     * @param idToFind id to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasId(String idToFind);

    /**
     * Checks if the element, or at least one element in a list of elements, has the class.
     * <p>
     * This is not an exact match validation of the value of the class attribute.
     *
     * @param classToFind class to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasClass(String classToFind);

    /**
     * Check if the element, or at least one element in a list of elements, has given value
     *
     * @param value value to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasValue(String value);

    /**
     * Checks if the element, or at least one element in a list of elements, has given name
     *
     * @param name name to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasName(String name);

    /**
     * Checks if the element, or at least one element in a list of elements, has given tag
     *
     * @param tagName tag name to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasTagName(String tagName);

    /**
     * Checks if the element, or at least one element in a list of elements, has property with the exact given value
     *
     * @param attribute attribute to find
     * @param value     property value to match with actual
     * @return {@code this} assertion object.
     */
    AbstractAssert hasAttributeValue(String attribute, String value);

    /**
     * Checks if the element, or at least one element in a list of elements, has given dimension
     *
     * @param dimension dimension to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasDimension(Dimension dimension);

}
