package org.fluentlenium.assertj.custom;

public interface ElementStateAssert {

    FluentWebElementAssert isClickable();
    FluentWebElementAssert isNotClickable();

    FluentWebElementAssert isDisplayed();
    FluentWebElementAssert isNotDisplayed();

    FluentWebElementAssert isEnabled();
    FluentWebElementAssert isNotEnabled();

    FluentWebElementAssert isSelected();
    FluentWebElementAssert isNotSelected();

}
