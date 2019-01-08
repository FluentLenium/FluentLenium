package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.Dimension;

public interface FluentAssert {

    AbstractAssert hasText(String textToFind);
    AbstractAssert hasTextMatching(String regexToBeMatched);
    AbstractAssert hasNotText(String textToFind);

    AbstractAssert hasId(String idToFind);
    AbstractAssert hasClass(String classToFind);
    AbstractAssert hasValue(String value);
    AbstractAssert hasName(String name);
    AbstractAssert hasTagName(String tagName);
    AbstractAssert hasAttributeValue(String attribute, String value);

    AbstractAssert hasSize(Dimension dimension);

}
