package org.fluentlenium.assertj.custom;

public interface ElementStateAssert {

    /**
     * check if the element is clickable
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isClickable();

    /**
     * check if the element is not clickable
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isNotClickable();

    /**
     * check if the element is displayed
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isDisplayed();

    /**
     * check if the element is not displayed
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isNotDisplayed();

    /**
     * check if the element is enabled
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isEnabled();


    /**
     * check if the element is not enabled
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isNotEnabled();

    /**
     * check if the element is selected
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isSelected();

    /**
     * check if the element is not selected
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isNotSelected();

}
