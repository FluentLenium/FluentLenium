package org.fluentlenium.assertj.custom;

/**
 * Builder for fluent list size assertion.
 */
public class FluentListSizeBuilder {

    private final int actualSize;
    private final FluentListAssert listAssert;

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

    public FluentListAssert lessThan(final int size) {
        if (this.actualSize >= size) {
            this.listAssert.internalFail("Actual size: " + actualSize + " is not less than: " + size);
        }
        return this.listAssert;
    }

    public Object lessThanOrEqualTo(final int size) {
        if (this.actualSize > size) {
            this.listAssert.internalFail("Actual size: " + actualSize + " is not less than or equal to: " + size);
        }
        return this.listAssert;
    }

    public FluentListAssert greaterThan(final int size) {
        if (this.actualSize <= size) {
            this.listAssert.internalFail("Actual size: " + actualSize + " is not greater than: " + size);
        }
        return this.listAssert;
    }

    public Object greaterThanOrEqualTo(final int size) {
        if (this.actualSize < size) {
            this.listAssert.internalFail("Actual size: " + actualSize + " is not greater than or equal to: " + size);
        }
        return this.listAssert;
    }

}
