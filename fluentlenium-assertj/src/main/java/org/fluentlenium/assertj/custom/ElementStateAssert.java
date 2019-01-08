package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;

public interface ElementStateAssert {

    AbstractAssert isClickable();
    AbstractAssert isNotClickable();

    AbstractAssert isDisplayed();
    AbstractAssert isNotDisplayed();

    AbstractAssert isEnabled();
    AbstractAssert isNotEnabled();

    AbstractAssert isSelected();
    AbstractAssert isNotSelected();

}
