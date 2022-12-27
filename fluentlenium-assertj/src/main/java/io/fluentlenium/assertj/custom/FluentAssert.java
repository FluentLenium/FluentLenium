package io.fluentlenium.assertj.custom;

import io.fluentlenium.core.domain.FluentWebElement;
import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.Dimension;

/**
 * Base assertion interface.
 */
public interface FluentAssert {

    /**
     * Checks if the element, or at least one element in a list of elements, contain the text.
     * <p>
     * Example:
     * <p>
     * For a {@link FluentWebElement} it can be:
     * <pre>
     * assertThat(element).hasText("magnificent");
     * </pre>
     * which passes when the element contains (but is not necessarily exactly equal to) the argument text.
     * <p>
     * NOTE: currently both this method and {@link #hasTextContaining(String)} validate text containment.
     * If you want to validate containment please use {@link #hasTextContaining(String)}, as this method will be updated
     * in a future release to validate equality of text(s).
     *
     * @param textToFind text to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasText(String textToFind);

    /**
     * Checks if the element, or at least one element in a list of elements, contain the text.
     * <p>
     * Example:
     * <p>
     * For a {@link FluentWebElement} it can be:
     * <pre>
     * assertThat(element).hasTextContaining("magnificent");
     * </pre>
     * which passes when the element contains (but is not necessarily exactly equal to) the argument text.
     * <p>
     * NOTE: currently both {@link #hasText(String)} and this method validate text containment. If you want to validate
     * containment please use this method, as {@link #hasText(String)} will be updated in a future release to validate
     * equality of text(s).
     *
     * @param text text to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasTextContaining(String text);

    /**
     * Checks if the element, or at least one element in a list of elements, matches the given regex.
     * <p>
     * Example:
     * <p>
     * For a {@link FluentWebElement} it can be:
     * <pre>
     * assertThat(element).hasTextMatching(".*magnificent$");
     * </pre>
     *
     * @param regexToBeMatched regex to be matched
     * @return {@code this} assertion object.
     */
    AbstractAssert hasTextMatching(String regexToBeMatched);

    /**
     * Checks if the element does not contain, or none of the elements in a list of elements contain the text.
     * <p>
     * Example:
     * <p>
     * For a {@link FluentWebElement} it can be:
     * <pre>
     * assertThat(element).hasNotText("magnificent");
     * </pre>
     * which passes when the element text doesn't contains (and not when it is not equal to) to the argument text.
     * <p>
     * NOTE: currently both this method and {@link #hasNotTextContaining(String)} validate text containment.
     * If you want to validate containment please use {@link #hasNotTextContaining(String)}, as this method will be updated
     * in a future release to validate equality of text(s).
     *
     * @param textToFind text to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasNotText(String textToFind);

    /**
     * Checks if the element does not contain, or none of the elements in a list of elements contain the text.
     * <p>
     * Example:
     * <p>
     * For a {@link FluentWebElement} it can be:
     * <pre>
     * assertThat(element).hasNotText("magnificent");
     * </pre>
     * which passes when the element text doesn't contains (and not when it is not equal to) to the argument text.
     * <p>
     * NOTE: currently both {@link #hasNotText(String)} and this method validate text containment. If you want to validate
     * containment please use this method, as {@link #hasNotText(String)} will be updated in a future release to validate
     * equality of text(s).
     *
     * @param textToFind text to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasNotTextContaining(String textToFind);

    /**
     * Checks if the element, or at least one element in a list of elements, has the given id
     * <p>
     * Example:
     * <p>
     * For a {@link FluentWebElement} it can be:
     * <pre>
     * assertThat(element).hasId("marked");
     * </pre>
     * which passes when the element text is equal to the argument {@code idToFind}.
     *
     * @param idToFind id to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasId(String idToFind);

    /**
     * Checks if the element, or at least one element in a list of elements, has the class.
     * <p>
     * Example:
     * <p>
     * For a {@link FluentWebElement} it can be:
     * <pre>
     * assertThat(element).hasClass("marked");
     * </pre>
     * which passes when the element class attribute (handled as a list of class values)
     * contains the argument {@code classToFind}.
     *
     * @param classToFind class to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasClass(String classToFind);

    /**
     * Checks if the element does not contain, or none of the elements in a list of elements contain the class
     * <p>
     * It passes assertion both when the class attribute is present but doesn't contain
     * the argument class, and when the class attribute is not present at all.
     * <p>
     * Example:
     * <p>
     * For a {@link FluentWebElement} it can be:
     * <pre>
     * assertThat(element).hasNotClass("marked");
     * </pre>
     *
     * @param htmlClass class to find the absence of
     * @return {@code this} assertion object.
     */
    AbstractAssert hasNotClass(String htmlClass);

    /**
     * Checks if the element, or at least one element in a list of elements, has all of the argument classes.
     * <p>
     * Example:
     * <p>
     * For a {@link FluentWebElement} it can be:
     * <pre>
     * assertThat(element).hasClasses("marked", "as", "great");
     * </pre>
     * which passes when the element class attribute (handled as a list of class values)
     * contains all of the argument {@code classesToFind}.
     *
     * @param classesToFind classes to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasClasses(String... classesToFind);

    /**
     * Checks if the element does not contain, or none of the elements in a list of elements contain any of the classes.
     * <p>
     * Example:
     * <p>
     * For a {@link FluentWebElement} it can be:
     * <pre>
     * assertThat(element).hasNotClasses("marked", "as", "great");
     * </pre>
     *
     * @param classesToFind classes to find the absence of
     * @return {@code this} assertion object.
     */
    AbstractAssert hasNotClasses(String... classesToFind);

    /**
     * Checks if the element, or at least one element in a list of elements has given value (in its 'value' attribute).
     * <p>
     * Example:
     * <p>
     * For a {@link FluentWebElement} it can be:
     * <pre>
     * assertThat(element).hasValue("John Smith");
     * </pre>
     * which passes when the element's value is equal to the argument value.
     *
     * @param value value to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasValue(String value);

    /**
     * Checks if the element, or at least one element in a list of elements, has given name (in its 'name' attribute).
     * <p>
     * Example:
     * <p>
     * For a {@link FluentWebElement} it can be:
     * <pre>
     * assertThat(element).hasName("John Smith");
     * </pre>
     * which passes when the element's value is equal to the argument name.
     *
     * @param name name to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasName(String name);

    /**
     * Checks if the element, or at least one element in a list of elements, has given tag.
     * <p>
     * Example:
     * <p>
     * For a {@link FluentWebElement} it can be:
     * <pre>
     * assertThat(element).hasTagName("div");
     * </pre>
     * which passes when the element's value is equal to the argument tag name.
     *
     * @param tagName tag name to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasTagName(String tagName);

    /**
     * Checks if the element, or at least one element in a list of elements, has property with the exact given value.
     * <p>
     * Example:
     * <p>
     * For a {@link FluentWebElement} it can be:
     * <pre>
     * assertThat(element).hasAttributeValue("href", "https://fluentlenium.io");
     * </pre>
     *
     * @param attribute attribute to find
     * @param value     property value to match with actual
     * @return {@code this} assertion object.
     */
    AbstractAssert hasAttributeValue(String attribute, String value);

    /**
     * Checks if the element, or at least one element in a list of elements, has given dimension.
     * <p>
     * Example:
     * <p>
     * For a {@link FluentWebElement} it can be:
     * <pre>
     * assertThat(element).hasDimension(fluentWebElement.getDimension());
     * </pre>
     * which passes when the element's dimension is equal to the argument dimension.
     *
     * @param dimension dimension to find
     * @return {@code this} assertion object.
     */
    AbstractAssert hasDimension(Dimension dimension);

}
