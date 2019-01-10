package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.Dimension;

import java.util.Arrays;
import java.util.List;

public class FluentListAssert extends AbstractAssert<FluentListAssert, FluentList>
    implements FluentAssert, ListStateAssert {

    public FluentListAssert(FluentList<FluentWebElement> actual) {
        super(actual, FluentListAssert.class);
    }

    public FluentListAssert isEmpty() {
        return hasSize(0);
    }

    public FluentListAssert isNotEmpty() {
        return hasSize().notEqualTo(0);
    }

    public FluentListAssert hasSize(int expectedSize) {
        int actualSize = actual.size();
        if (actualSize != expectedSize) {
            failWithMessage("Expected size: " + expectedSize
                    + ". Actual size: " + actualSize + ".");
        }
        return this;
    }

    public FluentListSizeBuilder hasSize() {
        return new FluentListSizeBuilder(actual.size(), this);
    }

    public FluentListAssert hasText(String textToFind) {
        List<String> actualTexts = actual.texts();
        checkListEmptiness(actualTexts);
        if (actualTexts.stream().noneMatch(text -> text.contains(textToFind))) {
            failWithMessage("No selected elements contains text: " + textToFind
                    + ". Actual texts found: " + actualTexts);
        }
        return this;
    }

    public FluentListAssert hasTextMatching(String regexToBeMatched) {
        List<String> actualTexts = actual.texts();
        checkListEmptiness(actualTexts);
        if (actualTexts.stream().noneMatch(text -> text.matches(regexToBeMatched))) {
            failWithMessage("No selected elements contains text matching: " + regexToBeMatched
                    + ". Actual texts found: " + actualTexts);
        }
        return this;
    }

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

    public FluentListAssert hasId(String idToFind) {
        List<String> actualIds = actual.ids();
        checkListEmptiness(actualIds);
        if (!actualIds.contains(idToFind)) {
            failWithMessage("No selected elements has id: " + idToFind
                    + ". Actual texts found : " + actualIds);
        }
        return this;
    }

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
                "No selected elements has class: " + classToFind
                        + ". Actual classes found : " + classesFromElement);
        return this;
    }

    @Override
    public FluentListAssert hasValue(String value) {
        return null;
    }

    @Override
    public FluentListAssert hasName(String name) {
        return null;
    }

    @Override
    public FluentListAssert hasTagName(String tagName) {
        return null;
    }

    @Override
    public FluentListAssert hasDimension(Dimension dimension) {
        return null;
    }

    @Override
    public FluentListAssert hasAttributeValue(String attribute, String value) {
        return null;
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
