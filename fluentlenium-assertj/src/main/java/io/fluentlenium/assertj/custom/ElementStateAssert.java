package io.fluentlenium.assertj.custom;

/**
 * Interface for asserting the state of an element.
 */
public interface ElementStateAssert {

    /**
     * Checks if the element is clickable.
     * <p>
     * This method also has a preceding validation for the presence of the element,
     * which fails this assertion method if it would fail.
     * <p>
     * Example:
     * <pre>
     * assertThat(element).isClickable();
     * </pre>
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isClickable();

    /**
     * Checks if the element is not clickable.
     * <p>
     * This method also has a preceding validation for the presence of the element,
     * which fails this assertion method if it would fail.
     * <p>
     * Example:
     * <pre>
     * assertThat(element).isNotClickable();
     * </pre>
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isNotClickable();

    /**
     * Checks if the element is displayed.
     * <p>
     * This method also has a preceding validation for the presence of the element,
     * which fails this assertion method if it would fail.
     * <p>
     * Example:
     * <pre>
     * assertThat(element).isDisplayed();
     * </pre>
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isDisplayed();

    /**
     * Checks if the element is not displayed.
     * <p>
     * This method also has a preceding validation for the presence of the element,
     * which fails this assertion method if it would fail.
     * <p>
     * Example:
     * <pre>
     * assertThat(element).isNotDisplayed();
     * </pre>
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isNotDisplayed();

    /**
     * Checks if the element is enabled.
     * <p>
     * This method also has a preceding validation for the presence of the element,
     * which fails this assertion method if it would fail.
     * <p>
     * Example:
     * <pre>
     * assertThat(element).isEnabled();
     * </pre>
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isEnabled();

    /**
     * Checks if the element is not enabled.
     * <p>
     * This method also has a preceding validation for the presence of the element,
     * which fails this assertion method if it would fail.
     * <p>
     * Example:
     * <pre>
     * assertThat(element).isNotEnabled();
     * </pre>
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isNotEnabled();

    /**
     * Checks if the element is selected.
     * <p>
     * This method also has a preceding validation for the presence of the element,
     * which fails this assertion method if it would fail.
     * <p>
     * Example:
     * <pre>
     * assertThat(element).isSelected();
     * </pre>
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isSelected();

    /**
     * Checks if the element is not selected.
     * <p>
     * This method also has a preceding validation for the presence of the element,
     * which fails this assertion method if it would fail.
     * <p>
     * Example:
     * <pre>
     * assertThat(element).isNotSelected();
     * </pre>
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isNotSelected();

    /**
     * Checks if the element is present.
     * <p>
     * Example:
     * <pre>
     * assertThat(element).isPresent();
     * </pre>
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isPresent();

    /**
     * Checks if the element is not present.
     * <p>
     * Example:
     * <pre>
     * assertThat(element).isNotPresent();
     * </pre>
     *
     * @return {@code this} assertion object.
     */
    FluentWebElementAssert isNotPresent();

}
