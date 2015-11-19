package org.fest.assertions.fluentlenium.custom;

import org.fest.assertions.GenericAssert;


public class AttributeAssert extends GenericAssert<AttributeAssert, String> {

    public AttributeAssert(String actual) {
        super(AttributeAssert.class, actual);
    }

    public AttributeAssert withValue(String expectedValue) {
        if (!actual.equals(expectedValue)) {
            super.fail("The attribute does not have the value: " + expectedValue + ". Actual value is : " + actual);
        }
        return this;
    }
}
