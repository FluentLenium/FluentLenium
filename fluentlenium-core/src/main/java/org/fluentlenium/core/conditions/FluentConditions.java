package org.fluentlenium.core.conditions;


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
     * Check that this element is visible and isEnabled such that you can click it.
     *
     * @return true if the element can be clicked, false otherwise.
     */
    boolean isClickable();

    /**
     * Check that this element is no longer attached to the DOM.
     *
     * @return false is the element is still attached to the DOM, true otherwise.
     */
    boolean isStale();

    /**
     * Check that this element is displayed.
     *
     * @return true if element is displayed, false otherwise.
     */
    boolean isDisplayed();

    /**
     * Check that this element is enabled.
     *
     * @return true if element is enabled, false otherwise.
     */
    boolean isEnabled();

    /**
     * Check that this element is selected.
     *
     * @return true if element is selected, false otherwise.
     */
    boolean isSelected();

    /**
     * Check that this element has the given id.
     *
     * @param id id to check
     * @return true if the element has the given id, false otherwise.
     */
    boolean hasId(String id);

    /**
     * Check that this element has the given name
     *
     * @param name name to check
     * @return true if the element has the given name, false otherwise.
     */
    boolean hasName(String name);

    /**
     * Check conditions on this element text.
     *
     * @return true if the element has the given text, false otherwise.
     * @see StringConditions#equals(Object)
     */
    boolean text(String anotherString);

    /**
     * Check conditions on this element text.
     *
     * @return An object to configure text conditions.
     */
    StringConditions text();

    /**
     * Check conditions on this element text content.
     *
     * @return true if the element has the given text content, false otherwise.
     * @see StringConditions#equals(Object)
     */
    boolean textContext(String anotherString);

    /**
     * Check conditions on this element text content.
     *
     * @return An object to configure text content conditions.
     */
    StringConditions textContent();

    /**
     * Check that the attribute has the given value.
     *
     * @param attribute attribute name to check
     * @param value     attribute value to check
     * @return true if the given attribute has the given value, false otherwise.
     */
    boolean hasAttribute(final String attribute, final String value);

    /**
     * check conditions on rectangle of this element
     *
     * @return An object to configure advanced position conditions
     */
    RectangleConditions hasRectangle();
}
