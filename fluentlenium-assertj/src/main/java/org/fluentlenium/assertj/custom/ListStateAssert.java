package org.fluentlenium.assertj.custom;

public interface ListStateAssert {

    FluentListSizeBuilder hasSize();
    FluentListAssert hasSize(int size);
    FluentListAssert isEmpty();
    FluentListAssert isNotEmpty();

}
