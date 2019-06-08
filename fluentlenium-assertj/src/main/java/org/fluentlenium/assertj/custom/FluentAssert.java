package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.Dimension;

/**
 * Base assertion interface.
 */
public interface FluentAssert {

    /**
     * check if the element or list of elements contains the text
     *
     * @param textToFind text to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasText(String textToFind);

    /**
     * check if the element or list of elements matches the given regex
     *
     * @param regexToBeMatched regex to be matched
     * @return {@code this} assertion object.
     */
    AbstractAssert hasTextMatching(String regexToBeMatched);

    /**
     * check if the element or list of elements does not contain the text
     *
     * @param textToFind text to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasNotText(String textToFind);

    /**
     * check if the element or list of elements has the given id
     *
     * @param idToFind id to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasId(String idToFind);

    /**
     * check if the element or list of elements has the class
     *
     * @param classToFind class to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasClass(String classToFind);

    /**
     * check if the element or list of elements has given value
     *
     * @param value value to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasValue(String value);

    /**
     * check if the element or list of elements has given name
     *
     * @param name name to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasName(String name);

    /**
     * check if the element or list of elements has given tag
     *
     * @param tagName tag name to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasTagName(String tagName);

    /**
     * check if the element or list of elements has property with given value
     *
     * @param attribute attribute to find
     * @param value     property value to match with actual
     * @return {@code this} assertion object.
     */
    AbstractAssert hasAttributeValue(String attribute, String value);

    /**
     * check if the element or list of elements has given dimension
     *
     * @param dimension dimension to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasDimension(Dimension dimension);

}
