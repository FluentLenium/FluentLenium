package org.fluentlenium.assertj.custom;

/**
 * Interface for asserting the state of an element.
 */
public interface ListStateAssert {

    /**
     * Creates a {@link FluentListSizeBuilder} object from which further size related assertion methods may be called.
     * <p>
     * This method doesn't do any assertion, only creates the new object.
     * <p>
     * Example:
     * <pre>
     * assertThat(elementList).hasSize().greaterThanOrEqualTo(5);
     * </pre>
     *
     * @return a new ist size builder
     */
    FluentListSizeBuilder hasSize();

    /**
     * Checks whether the list size is the same as the expected one in the argument.
     * <p>
     * Example:
     * <pre>
     * assertThat(elementList).hasSize(5);
     * </pre>
     *
     * @param expectedSize expected size
     * @return this assertion object
     */
    FluentListAssert hasSize(int expectedSize);

    /**
     * Checks if the list is empty.
     * <p>
     * Example:
     * <pre>
     * assertThat(elementList).isEmpty();
     * </pre>
     *
     * @return this assertion object
     */
    FluentListAssert isEmpty();

    /**
     * Checks if the list is not empty.
     * <p>
     * Example:
     * <pre>
     * assertThat(elementList).isNotEmpty();
     * </pre>
     *
     * @return this assertion object
     */
    FluentListAssert isNotEmpty();

}
