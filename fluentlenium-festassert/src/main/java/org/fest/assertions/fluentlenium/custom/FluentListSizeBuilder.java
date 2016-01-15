package org.fest.assertions.fluentlenium.custom;

/**
 * @deprecated fest-assert has not been maintained since 2013. This module will be removed from FluentLenium in a next future. Use fluentlenium-assertj instead.
 */
@Deprecated
public class FluentListSizeBuilder {

    private int actualSize;
    private FluentListAssert listAssert;

    public FluentListSizeBuilder(int size, FluentListAssert listeAssert) {
        this.actualSize = size;
        this.listAssert = listeAssert;
    }

    public FluentListAssert lessThan(int size) {
        if (this.actualSize >= size) {
            this.listAssert.internalFail("Actual size: " + actualSize + " is not less than: " + size);
        }
        return this.listAssert;
    }

    public Object lessThanOrEqualTo(int size) {
        if (this.actualSize > size) {
            this.listAssert.internalFail("Actual size: " + actualSize + " is not less than or equal to: " + size);
        }
        return this.listAssert;
    }

    public FluentListAssert greaterThan(int size) {
        if (this.actualSize <= size) {
            this.listAssert.internalFail("Actual size: " + actualSize + " is not greater than: " + size);
        }
        return this.listAssert;
    }

    public Object greaterThanOrEqualTo(int size) {
        if (this.actualSize < size) {
            this.listAssert.internalFail("Actual size: " + actualSize + " is not greater than or equal to: " + size);
        }
        return this.listAssert;
    }

}
