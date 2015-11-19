package org.fluentlenium.assertj.custom;

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
