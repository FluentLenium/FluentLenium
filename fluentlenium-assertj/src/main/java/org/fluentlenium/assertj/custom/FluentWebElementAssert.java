package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.Dimension;

import java.util.Arrays;
import java.util.List;

public class FluentWebElementAssert extends AbstractAssert<FluentWebElementAssert, FluentWebElement>
        implements ElementStateAssert, FluentAssert {

    public FluentWebElementAssert(FluentWebElement actual) {
        super(actual, FluentWebElementAssert.class);
    }

    @Override
    public FluentWebElementAssert isEnabled() {
        if (!actual.enabled()) {
            failWithMessage("Element in assertion is present but not enabled");
        }
        return this;
    }

    @Override
    public FluentWebElementAssert isNotEnabled() {
        if (actual.enabled()) {
            failWithMessage("Element in assertion is present but enabled");
        }
        return this;
    }

    @Override
    public FluentWebElementAssert isDisplayed() {
        if (!actual.displayed()) {
            failWithMessage("Element in assertion is present but not displayed");
        }

        return this;
    }

    @Override
    public FluentWebElementAssert isNotDisplayed() {
        if (actual.displayed()) {
            failWithMessage("Element in assertion is present but displayed");
        }
        return this;
    }

    @Override
    public FluentWebElementAssert isSelected() {
        if (!actual.selected()) {
            failWithMessage("Element in assertion is present but not selected");
        }
        return this;
    }

    @Override
    public FluentWebElementAssert isNotSelected() {
        if (actual.selected()) {
            failWithMessage("Element in assertion is present but selected");
        }
        return this;
    }

    @Override
    public FluentWebElementAssert isClickable() {
        if (!actual.clickable()) {
            failWithMessage("Element in assertion is present but not clickable");
        }
        return this;
    }

    @Override
    public FluentWebElementAssert isNotClickable() {
        if (actual.clickable()) {
            failWithMessage("Element in assertion is present but clickable");
        }
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
            failWithMessage("The element contain the text: " + textToFind);
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
        String actualClasses = actual.attribute("class");
        if (!getClasses(actualClasses).contains(classToFind)) {
            failWithMessage("The element does not have the class: " + classToFind
                    + ". Actual class found : " + actualClasses);
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
        String actualValue;

        actualValue = actual.attribute(attribute);

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

    private List<String> getClasses(String classString) {
        String[] primitiveList = classString.split(" ");
        return Arrays.asList(primitiveList);
    }
}
