package org.fluentlenium.assertj.custom;

public interface ListStateAssert {

    /**
     * Check the list size
     *
     * @return List size builder
     */
    FluentListSizeBuilder hasSize();

    /**
     * Check the list size
     *
     * @param expectedSize expected size
     * @return assertion object
     */
    FluentListAssert hasSize(int expectedSize);

    /**
     * Check is list is empty
     *
     * @return assertion object
     */
    FluentListAssert isEmpty();

    /**
     * Check is list is not empty
     *
     * @return assertion object
     */
    FluentListAssert isNotEmpty();

}
