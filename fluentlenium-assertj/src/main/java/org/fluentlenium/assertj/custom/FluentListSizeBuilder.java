package org.fluentlenium.assertj.custom;

/**
 * Builder for fluent list size assertion.
 */
public class FluentListSizeBuilder {

    private final int actualSize;
    private final FluentListAssert listAssert;

    private static final String ACTUAL_SIZE = "Actual size: ";

    /**
     * Creates a new fluent list size builder.
     *
     * @param size       size of the list
     * @param listAssert assertion
     */
    public FluentListSizeBuilder(final int size, final FluentListAssert listAssert) {
        this.actualSize = size;
        this.listAssert = listAssert;
    }

    /**
     * Assert that actual list size is less that given size.
     *
     * @param size expected size
     * @return ${code this} assertion object.
     */
    public FluentListAssert lessThan(final int size) {
        if (this.actualSize >= size) {
            this.listAssert.internalFail(ACTUAL_SIZE + actualSize + " is not less than: " + size);
        }
        return this.listAssert;
    }

    /**
     * Assert that actual list size is less than or equal to given size.
     *
     * @param size expected size
     * @return ${code this} assertion object.
     */
    public Object lessThanOrEqualTo(final int size) {
        if (this.actualSize > size) {
            this.listAssert.internalFail(ACTUAL_SIZE + actualSize + " is not less than or equal to: " + size);
        }
        return this.listAssert;
    }

    /**
     * Assert that actual list size is greater than given size.
     *
     * @param size expected size
     * @return ${code this} assertion object.
     */
    public FluentListAssert greaterThan(final int size) {
        if (this.actualSize <= size) {
            this.listAssert.internalFail(ACTUAL_SIZE + actualSize + " is not greater than: " + size);
        }
        return this.listAssert;
    }

    /**
     * Assert that actual list size is greater than or equal to given size.
     *
     * @param size expected size
     * @return ${code this} assertion object.
     */
    public Object greaterThanOrEqualTo(final int size) {
        if (this.actualSize < size) {
            this.listAssert.internalFail(ACTUAL_SIZE + actualSize + " is not greater than or equal to: " + size);
        }
        return this.listAssert;
    }

}
