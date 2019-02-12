package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.Dimension;

import java.util.Arrays;
import java.util.List;

public class FluentListAssert extends AbstractAssert<FluentListAssert, FluentList>
    implements FluentAssert, ListStateAssert {

    public FluentListAssert(FluentList<? extends FluentWebElement> actual) {
        super(actual, FluentListAssert.class);
    }

    @Override
    public FluentListAssert isEmpty() {
        return hasSize(0);
    }

    @Override
    public FluentListAssert isNotEmpty() {
        return hasSize().notEqualTo(0);
    }

    @Override
    public FluentListAssert hasSize(int expectedSize) {
        int actualSize = actual.size();
        if (actualSize != expectedSize) {
            failWithMessage("Expected size: " + expectedSize
                    + ". Actual size: " + actualSize + ".");
        }
        return this;
    }

    @Override
    public FluentListSizeBuilder hasSize() {
        return new FluentListSizeBuilder(actual.size(), this);
    }

    @Override
    public FluentListAssert hasText(String textToFind) {
        List<String> actualTexts = actual.texts();
        checkListEmptiness(actualTexts);
        if (actualTexts.stream().noneMatch(text -> text.contains(textToFind))) {
            failWithMessage("No selected elements contains text: " + textToFind
                    + ". Actual texts found: " + actualTexts);
        }
        return this;
    }

    @Override
    public FluentListAssert hasTextMatching(String regexToBeMatched) {
        List<String> actualTexts = actual.texts();
        checkListEmptiness(actualTexts);
        if (actualTexts.stream().noneMatch(text -> text.matches(regexToBeMatched))) {
            failWithMessage("No selected elements contains text matching: " + regexToBeMatched
                    + ". Actual texts found: " + actualTexts);
        }
        return this;
    }

    @Override
    public FluentListAssert hasNotText(String textToFind) {
        List<String> actualTexts = actual.texts();
        checkListEmptiness(actualTexts);
        for (String text : actualTexts) {
            if (text.contains(textToFind)) {
                failWithMessage(
                        "At least one selected elements contains text: " + textToFind
                                + ". Actual texts found: " + actualTexts);
            }
        }
        return this;
    }

    @Override
    public FluentListAssert hasId(String idToFind) {
        List<String> actualIds = actual.ids();
        checkListEmptiness(actualIds);
        if (!actualIds.contains(idToFind)) {
            failWithMessage("No selected elements have id: " + idToFind
                    + ". Actual ids found : " + actualIds);
        }
        return this;
    }

    @Override
    public FluentListAssert hasClass(String classToFind) {
        List<String> classes = actual.attributes("class");
        checkListEmptiness(classes);

        for (String classesStr : classes) {
            List<String> classesLst = Arrays.asList(classesStr.split(" "));
            if (classesLst.contains(classToFind)) {
                return this;
            }
        }

        String classesFromElement = String.join(", ", classes);
        failWithMessage(
                "No selected elements have class: " + classToFind
                        + ". Actual classes found : " + classesFromElement);
        return this;
    }

    @Override
    public FluentListAssert hasValue(String value) {
        List<String> actualValues = actual.values();
        checkListEmptiness(actualValues);
        if (!actualValues.contains(value)) {
            failWithMessage("No selected elements have value: " + value
                    + ". Actual values found : " + actualValues);
        }
        return this;
    }

    @Override
    public FluentListAssert hasName(String name) {
        List<String> actualNames = actual.names();
        checkListEmptiness(actualNames);
        if (!actualNames.contains(name)) {
            failWithMessage("No selected elements have name: " + name
                    + ". Actual names found : " + actualNames);
        }
        return this;
    }

    @Override
    public FluentListAssert hasTagName(String tagName) {
        List<String> actualTags = actual.tagNames();
        checkListEmptiness(actualTags);
        if (!actualTags.contains(tagName)) {
            failWithMessage("No selected elements have tag: " + tagName
                    + ". Actual tags found : " + actualTags);
        }
        return this;
    }

    @Override
    public FluentListAssert hasDimension(Dimension dimension) {
        List<Dimension> actualDimensions = actual.dimensions();
        checkListEmptiness(actualDimensions);
        if (!actualDimensions.contains(dimension)) {
            failWithMessage("No selected elements have dimension: " + dimension.toString()
                    + ". Actual dimensions found : " + actualDimensions.toString());
        }
        return this;
    }

    @Override
    public FluentListAssert hasAttributeValue(String attribute, String value) {
        List<String> actualValues = actual.attributes(attribute);
        checkListEmptiness(actualValues);
        if (!actualValues.contains(value)) {
            failWithMessage("No selected elements have attribute " + attribute
                    + " with value: " + value + ". Actual values found: " + actualValues);
        }
        return this;
    }

    void failWithMessage(String errorMessage) {
        super.failWithMessage(errorMessage);
    }

    private void checkListEmptiness(List<?> elements) {
        if (elements.isEmpty()) {
            throw new AssertionError("List is empty. Please make sure you use correct selector.");
        }
    }
}
