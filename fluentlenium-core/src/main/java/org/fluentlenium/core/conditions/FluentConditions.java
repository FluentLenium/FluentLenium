package org.fluentlenium.core.conditions;


import org.fluentlenium.core.conditions.message.Message;
import org.fluentlenium.core.conditions.message.MessageContext;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Conditions API for elements.
 */
public interface FluentConditions extends Conditions<FluentWebElement> {
    /**
     * Negates this condition object.
     *
     * @return a new negated condition object
     */
    FluentConditions not();

    /**
     * Check that this element is visible and enabled such that you can click it.
     *
     * @return true if the element can be clicked, false otherwise.
     */
    @Message("should [not ]be clickable")
    boolean clickable();

    /**
     * Check that this element is no longer attached to the DOM.
     *
     * @return false is the element is still attached to the DOM, true otherwise.
     */
    @Message("should [not ]be stale")
    boolean stale();

    /**
     * Check that this element is displayed.
     *
     * @return true if element is displayed, false otherwise.
     */
    @Message("should [not ]be displayed")
    boolean displayed();

    /**
     * Check that this element is enabled.
     *
     * @return true if element is enabled, false otherwise.
     */
    @Message("should [not ]be enabled")
    boolean enabled();

    /**
     * Check that this element is selected.
     *
     * @return true if element is selected, false otherwise.
     */
    @Message("should [not ]be selected")
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
    @Message("should [not ] have id=\"{0}\"")
    boolean id(String id);

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
    @Message("should [not ] have name=\"{0}\"")
    boolean name(String name);

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
    @Message("should [not ] have tagName=\"{0}\"")
    boolean tagName(String tagName);

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
    @Message("should [not ] have value=\"{0}\"")
    boolean value(String value);

    /**
     * Check conditions on this element text.
     *
     * @return true if the element has the given text, false otherwise.
     * @see StringConditions#equals(Object)
     */
    @Message("should [not ] have text=\"{0}\"")
    boolean text(String anotherString);

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
     * @return true if the element has the given text content, false otherwise.
     * @see StringConditions#equals(Object)
     */
    @Message("should [not ] have textContent=\"{0}\"")
    boolean textContext(String anotherString);

    /**
     * Check conditions on this element text content.
     *
     * @return An object to configure text content conditions.
     */
    @MessageContext("textContext")
    StringConditions textContent();

    /**
     * Check that the attribute has the given value.
     *
     * @param name  attribute name to check
     * @param value attribute value to check
     * @return true if the given attribute has the given value, false otherwise.
     */
    @Message("should [not ] have attribute \"{0}\"=\"{1}\"")
    boolean attribute(final String name, final String value);

    /**
     * Check conditions on the given attribute the attribute has the given value.
     *
     * @param name attribute name to check
     * @return An object to configure text attribute value conditions.
     */
    @MessageContext("attribute(\"{0}\"")
    StringConditions attribute(final String name);

    /**
     * check conditions on rectangle of this element
     *
     * @return An object to configure advanced position conditions
     */
    @MessageContext("rectangle(\"{0}\"")
    RectangleConditions rectangle();
}
