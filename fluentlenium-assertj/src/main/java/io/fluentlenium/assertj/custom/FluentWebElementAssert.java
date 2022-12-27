package io.fluentlenium.assertj.custom;

import io.fluentlenium.core.domain.FluentWebElement;
import io.netty.util.internal.StringUtil;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.StringAssert;
import io.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.Dimension;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static io.fluentlenium.assertj.custom.HtmlConstants.CLASS_ATTRIBUTE;

/**
 * Default implementation for {@link FluentWebElement} assertions.
 */
public class FluentWebElementAssert extends AbstractFluentAssert<FluentWebElementAssert, FluentWebElement>
        implements ElementStateAssert, ElementAttributeAssert {

    public FluentWebElementAssert(FluentWebElement actual) {
        super(actual, FluentWebElementAssert.class);
    }

    @Override
    public FluentWebElementAssert isEnabled() {
        isPresentAndIs(() -> !actual.enabled(), "Element in assertion is present but not enabled");
        return this;
    }

    @Override
    public FluentWebElementAssert isNotEnabled() {
        isPresentAndIs(() -> actual.enabled(), "Element in assertion is present but enabled");
        return this;
    }

    @Override
    public FluentWebElementAssert isDisplayed() {
        isPresentAndIs(() -> !actual.displayed(), "Element in assertion is present but not displayed");
        return this;
    }

    @Override
    public FluentWebElementAssert isNotDisplayed() {
        isPresentAndIs(() -> actual.displayed(), "Element in assertion is present but displayed");
        return this;
    }

    @Override
    public FluentWebElementAssert isSelected() {
        isPresentAndIs(() -> !actual.selected(), "Element in assertion is present but not selected");
        return this;
    }

    @Override
    public FluentWebElementAssert isNotSelected() {
        isPresentAndIs(() -> actual.selected(), "Element in assertion is present but selected");
        return this;
    }

    @Override
    public FluentWebElementAssert isClickable() {
        isPresentAndIs(() -> !actual.clickable(), "Element in assertion is present but not clickable");
        return this;
    }

    @Override
    public FluentWebElementAssert isNotClickable() {
        isPresentAndIs(() -> actual.clickable(), "Element in assertion is present but clickable");
        return this;
    }

    @Override
    public FluentWebElementAssert isPresent() {
        if (!actual.present()) {
            failWithMessage("Element in assertion is not present");
        }
        return this;
    }

    @Override
    public FluentWebElementAssert isNotPresent() {
        if (actual.present()) {
            failWithMessage("Element in assertion is present");
        }
        return this;
    }

    @Override
    public FluentWebElementAssert hasText(String textToFind) {
        String actualText = actual.text();
        if (!actualText.contains(textToFind)) {
            failWithMessage("The element does not contain the text: " + textToFind
                    + ". Actual text found : " + actualText);
        }
        return this;
    }

    @Override
    public FluentWebElementAssert hasTextContaining(String text) {
        String actualText = actual.text();
        if (!actualText.contains(text)) {
            failWithMessage("The element does not contain the text: " + text
                + ". Actual text found : " + actualText);
        }
        return this;
    }

    @Override
    public FluentWebElementAssert hasTextMatching(String regexToBeMatched) {
        String actualText = actual.text();
        if (!actualText.matches(regexToBeMatched)) {
            failWithMessage("The element does not match the regex: " + regexToBeMatched
                    + ". Actual text found : " + actualText);
        }
        return this;
    }

    @Override
    public FluentWebElementAssert hasNotText(String textToFind) {
        if (actual.text().contains(textToFind)) {
            failWithMessage("The element contains the text: " + textToFind);
        }
        return this;
    }

    @Override
    public FluentWebElementAssert hasNotTextContaining(String textToFind) {
        if (actual.text().contains(textToFind)) {
            failWithMessage("The element contains the text: " + textToFind);
        }
        return this;
    }

    @Override
    public FluentWebElementAssert hasId(String idToFind) {
        String actualId = actual.id();
        if (!actualId.equals(idToFind)) {
            failWithMessage("The element does not have the id: " + idToFind
                    + ". Actual id found : " + actualId);
        }
        return this;
    }

    @Override
    public FluentWebElementAssert hasClass(String classToFind) {
        String actualClasses = actual.attribute(CLASS_ATTRIBUTE);
        if (!getClasses(actualClasses).contains(classToFind)) {
            failWithMessage("The element does not have the class: " + classToFind
                    + ". Actual class found : " + actualClasses);
        }
        return this;
    }

    @Override
    public FluentWebElementAssert hasNotClass(String htmlClass) {
        String actualClasses = actual.attribute(CLASS_ATTRIBUTE);
        if (actualClasses != null && getClasses(actualClasses).contains(htmlClass)) {
            failWithMessage("The element has class: " + htmlClass);
        }
        return this;
    }

    @Override
    public FluentWebElementAssert hasClasses(String... classesToFind) {
        String actualClasses = actual.attribute(CLASS_ATTRIBUTE);

        if (StringUtil.isNullOrEmpty(actualClasses)) {
            failWithMessage("The element has no class attribute.");
        }

        if (!getClasses(actualClasses).containsAll(Arrays.asList(classesToFind))) {
            failWithMessage("The element does not have all classes: " + Arrays.toString(classesToFind)
                    + ". Actual classes found : " + actualClasses);
        }
        return this;
    }

    @Override
    public FluentWebElementAssert hasNotClasses(String... classesToFind) {
        String actualClasses = actual.attribute(CLASS_ATTRIBUTE);
        if (actualClasses != null) {
            List<String> actualClassesAsList = getClasses(actualClasses);
            if (actualClassesAsList.containsAll(Arrays.asList(classesToFind))) {
                failWithMessage("The element has the classes: " + Arrays.toString(classesToFind)
                        + ". Actual classes found : " + actualClasses);
            }
        }
        return this;
    }

    @Override
    public FluentWebElementAssert hasValue(String value) {
        String actualValue = actual.value();
        if (!actualValue.equals(value)) {
            failWithMessage("The element does not have the value: " + value
                    + ". Actual value found : " + actualValue);
        }
        return this;
    }

    @Override
    public FluentWebElementAssert hasName(String name) {
        String actualName = actual.name();
        if (!actualName.equals(name)) {
            failWithMessage("The element does not have the name: " + name
                    + ". Actual name found : " + actualName);
        }
        return this;
    }

    @Override
    public FluentWebElementAssert hasTagName(String tagName) {
        String actualTag = actual.tagName();
        if (!actualTag.equals(tagName)) {
            failWithMessage("The element does not have tag: " + tagName
                    + ". Actual tag found : " + actualTag);
        }
        return this;
    }

    @Override
    public FluentWebElementAssert hasDimension(Dimension dimension) {
        Dimension actualSize = actual.size();
        if (!actualSize.equals(dimension)) {
            failWithMessage("The element does not have the same size: " + dimension.toString()
                    + ". Actual size found : " + actualSize.toString());
        }
        return this;
    }

    @Override
    public FluentWebElementAssert hasAttributeValue(String attribute, String value) {
        String actualValue = actual.attribute(attribute);

        if (actualValue == null) {
            failWithMessage("The element does not have attribute " + attribute);
        }

        if (!actualValue.equals(value)) {
            failWithMessage("The " + attribute + " attribute "
                    + "does not have the value: " + value
                    + ". Actual value : " + actualValue);
        }
        return this;
    }

    @Override
    public AbstractStringAssert hasAttribute(String attribute) {
        String actualValue = actual.attribute(attribute);

        if (StringUtil.isNullOrEmpty(actualValue)) {
            failWithMessage("The element does not have attribute " + attribute);
        }

        return new StringAssert(actualValue);
    }

    @Override
    public FluentWebElementAssert hasNotAttribute(String attribute) {
        if (!StringUtil.isNullOrEmpty(actual.attribute(attribute))) {
            failWithMessage("The element has attribute " + attribute);
        }
        return this;
    }

    private void isPresentAndIs(Supplier<Boolean> elementCondition, String message) {
        isPresent();
        if (elementCondition.get()) {
            failWithMessage(message);
        }
    }
}
