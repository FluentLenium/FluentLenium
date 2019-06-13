package org.fluentlenium.assertj.custom;

/**
 * Builder for fluent list size assertion.
 */
public class FluentListSizeBuilder {

    private static final String ACTUAL_SIZE = "Actual size: ";

    private final int actualSize;
    private final FluentListAssert listAssert;

    /**
     * Creates a new fluent list size builder.
     *
     * @param size       the actual size of the list
     * @param listAssert assertion
     */
    FluentListSizeBuilder(int size, FluentListAssert listAssert) {
        actualSize = size;
        this.listAssert = listAssert;
    }

    /**
     * Assert that actual list size is less that given size.
     *
     * @param size expected size
     * @return ${code this} assertion object.
     */
    public FluentListAssert lessThan(int size) {
        if (actualSize >= size) {
            listAssert.failWithMessage(ACTUAL_SIZE + actualSize + " is not less than: " + size);
        }
        return listAssert;
    }

    /**
     * Assert that actual list size is less than or equal to given size.
     *
     * @param size expected size
     * @return ${code this} assertion object.
     */
    public FluentListAssert lessThanOrEqualTo(int size) {
        if (actualSize > size) {
            listAssert.failWithMessage(ACTUAL_SIZE + actualSize + " is not less than or equal to: " + size);
        }
        return listAssert;
    }

    /**
     * Assert that actual list size is greater than given size.
     *
     * @param size expected size
     * @return ${code this} assertion object.
     */
    public FluentListAssert greaterThan(int size) {
        if (actualSize <= size) {
            listAssert.failWithMessage(ACTUAL_SIZE + actualSize + " is not greater than: " + size);
        }
        return listAssert;
    }

    /**
     * Assert that actual list size is greater than or equal to given size.
     *
     * @param size expected size
     * @return ${code this} assertion object.
     */
    public FluentListAssert greaterThanOrEqualTo(int size) {
        if (actualSize < size) {
            listAssert.failWithMessage(ACTUAL_SIZE + actualSize + " is not greater than or equal to: " + size);
        }
        return listAssert;
    }

    /**
     * Assert that actual list size is different to given size.
     *
     * @param size expected size
     * @return ${code this} assertion object.
     */
    public FluentListAssert notEqualTo(int size) {
        if (actualSize == size) {
            listAssert.failWithMessage(ACTUAL_SIZE + actualSize + " is equal to: " + size);
        }
        return listAssert;
    }

    /**
     * Assert that actual list size is equal to given size.
     * <p>
     * As an alternate solution you can call the following assertion directly:
     * <pre>
     * assertThat(elementList).hasSize(5);
     * </pre>
     *
     * @param size expected size
     * @return ${code this} assertion object.
     */
    public FluentListAssert equalTo(int size) {
        return listAssert.hasSize(size);
    }

}
