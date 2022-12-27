package io.fluentlenium.core.conditions;

import io.fluentlenium.core.conditions.message.Message;import io.fluentlenium.core.conditions.message.MessageContext;import io.fluentlenium.core.conditions.message.NotMessage;import io.fluentlenium.core.conditions.message.Message;
import io.fluentlenium.core.conditions.message.MessageContext;
import io.fluentlenium.core.conditions.message.NotMessage;
import io.fluentlenium.core.domain.FluentWebElement;

/**
 * Conditions API for elements.
 */
public interface FluentConditions extends Conditions<FluentWebElement> {
    /**
     * Negates this condition object.
     *
     * @return a new negated condition object
     */
    @Negation
    FluentConditions not();

    /**
     * Check that this element is present
     *
     * @return true if the element is present, false otherwise.
     */
    @Message("is present")
    @NotMessage("is not present")
    boolean present();

    /**
     * Check that this element is visible and enabled such that you can click it.
     *
     * @return true if the element can be clicked, false otherwise.
     */
    @Message("is clickable")
    @NotMessage("is not clickable")
    boolean clickable();

    /**
     * Check that this element is no longer attached to the DOM.
     *
     * @return false if the element is still attached to the DOM, true otherwise.
     */
    @Message("is stale")
    @NotMessage("is not stale")
    boolean stale();

    /**
     * Check that this element is displayed.
     *
     * @return true if element is displayed, false otherwise.
     */
    @Message("is displayed")
    @NotMessage("is not displayed")
    boolean displayed();

    /**
     * Check that this element is enabled.
     *
     * @return true if element is enabled, false otherwise.
     */
    @Message("is enabled")
    @NotMessage("is not enabled")
    boolean enabled();

    /**
     * Check that this element is selected.
     *
     * @return true if element is selected, false otherwise.
     */
    @Message("is selected")
    @NotMessage("is not selected")
    boolean selected();

    /**
     * Check conditions on this element id.
     *
     * @return An object to configure id conditions.
     */
    @MessageContext("id")
    StringConditions id();

    /**
     * Check that this element has the given id.
     *
     * @param id id to check
     * @return true if the element has the given id, false otherwise.
     */
    @Message("has id=\"{0}\"")
    @NotMessage("does not have id=\"{0}\"")
    default boolean id(String id) {
        return id().equalTo(id);
    }

    /**
     * Check conditions on this element name.
     *
     * @return An object to configure name conditions.
     */
    @MessageContext("name")
    StringConditions name();

    /**
     * Check that this element has the given name
     *
     * @param name name to check
     * @return true if the element has the given name, false otherwise.
     */
    @Message("has name=\"{0}\"")
    @NotMessage("does not have name=\"{0}\"")
    default boolean name(String name) {
        return name().equalTo(name);
    }

    /**
     * Check conditions on this element tagName.
     *
     * @return An object to configure tagName conditions.
     */
    @MessageContext("tagName")
    StringConditions tagName();

    /**
     * Check that this element has the given tagName
     *
     * @param tagName tagName to check
     * @return true if the element has the given tagName, false otherwise.
     */
    @Message("has tagName=\"{0}\"")
    @NotMessage("does not have tagName=\"{0}\"")
    default boolean tagName(String tagName) {
        return tagName().equalTo(tagName);
    }

    /**
     * Check conditions on this element value.
     *
     * @return An object to configure value conditions.
     */
    @MessageContext("value")
    StringConditions value();

    /**
     * Check that this element has the given value
     *
     * @param value value to check
     * @return true if the element has the given value, false otherwise.
     */
    @Message("has value=\"{0}\"")
    @NotMessage("does not have value=\"{0}\"")
    default boolean value(String value) {
        return value().equalTo(value);
    }

    /**
     * Check that this element has the given text.
     *
     * @param text string to compare with
     * @return true if the element has the given text, false otherwise.
     * @see StringConditions#equalTo(String)
     */
    @Message("has text=\"{0}\"")
    @NotMessage("does not have text=\"{0}\"")
    default boolean text(String text) {
        return text().equalTo(text);
    }

    /**
     * Check conditions on this element text.
     *
     * @return An object to configure text conditions.
     */
    @MessageContext("text")
    StringConditions text();

    /**
     * Check conditions on this element text content.
     *
     * @param anotherString string to compare with
     * @return true if the element has the given text content, false otherwise.
     * @see StringConditions#equalTo(String)
     */
    @Message("has textContent=\"{0}\"")
    @NotMessage("does not have textContent=\"{0}\"")
    default boolean textContent(String anotherString) {
        return textContent().equalTo(anotherString);
    }

    /**
     * Check conditions on this element text content.
     *
     * @return An object to configure text content conditions.
     */
    @MessageContext("textContent")
    StringConditions textContent();

    /**
     * Check that the attribute has the given value.
     *
     * @param name  attribute name to check
     * @param value attribute value to check
     * @return true if the given attribute has the given value, false otherwise.
     */
    @Message("has attribute \"{0}\"=\"{1}\"")
    @NotMessage("does not have attribute \"{0}\"=\"{1}\"")
    default boolean attribute(String name, String value) {
        return attribute(name).equalTo(value);
    }

    /**
     * Check conditions on the given attribute the attribute has the given value.
     *
     * @param name attribute name to check
     * @return An object to configure text attribute value conditions.
     */
    @MessageContext("attribute(\"{0}\"")
    StringConditions attribute(String name);

    /**
     * check conditions on rectangle of this element
     *
     * @return An object to configure advanced position conditions
     */
    @MessageContext("rectangle")
    RectangleConditions rectangle();

    /**
     * Check that the class attribute has the given class name.
     *
     * @param className class name
     * @return true if it has the given class name, false otherwise.
     */
    @Message("has class \"{0}\"")
    @NotMessage("does not have class \"{0}\"")
    boolean className(String className);

}
