package io.fluentlenium.assertj.custom;

import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import org.assertj.core.api.ListAssert;
import org.openqa.selenium.Dimension;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static io.fluentlenium.assertj.custom.HtmlConstants.CLASS_ATTRIBUTE;

/**
 * Default implementation for {@link FluentList} assertions.
 */
@SuppressWarnings("unchecked")
public class FluentListAssert extends AbstractFluentAssert<FluentListAssert, FluentList>
        implements ListStateAssert, ListAttributeAssert {

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
        int actualSize = actual.count();
        if (actualSize != expectedSize) {
            failWithMessage("Expected size: " + expectedSize + ". Actual size: " + actualSize + ".");
        }
        return this;
    }

    @Override
    public FluentListSizeBuilder hasSize() {
        return new FluentListSizeBuilder(actual.count(), this);
    }

    @Override
    public FluentListAssert hasText(String textToFind) {
        List<String> actualTexts = requiresNonEmpty(actual.texts());
        if (actualTexts.stream().noneMatch(text -> text.contains(textToFind))) {
            failWithMessage("No selected elements contains text: " + textToFind
                    + ". Actual texts found: " + actualTexts);
        }
        return this;
    }

    @Override
    public FluentListAssert hasTextContaining(String textToFind) {
        List<String> actualTexts = requiresNonEmpty(actual.texts());
        if (actualTexts.stream().noneMatch(text -> text.contains(textToFind))) {
            failWithMessage("No selected elements contains text: " + textToFind
                    + ". Actual texts found: " + actualTexts);
        }
        return this;
    }

    @Override
    public FluentListAssert hasTextMatching(String regexToBeMatched) {
        List<String> actualTexts = requiresNonEmpty(actual.texts());
        if (actualTexts.stream().noneMatch(text -> text.matches(regexToBeMatched))) {
            failWithMessage("No selected elements contains text matching: " + regexToBeMatched
                    + ". Actual texts found: " + actualTexts);
        }
        return this;
    }

    @Override
    public FluentListAssert hasNotText(String textToFind) {
        List<String> actualTexts = requiresNonEmpty(actual.texts());
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
    public FluentListAssert hasNotTextContaining(String textToFind) {
        List<String> actualTexts = requiresNonEmpty(actual.texts());
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
        List<String> actualIds = requiresNonEmpty(actual.ids());
        if (!actualIds.contains(idToFind)) {
            failWithMessage("No selected elements have id: " + idToFind
                    + ". Actual ids found : " + actualIds);
        }
        return this;
    }

    @Override
    public FluentListAssert hasClass(String classToFind) {
        return validateHasClasses("No selected elements have class: ", classToFind);
    }

    @Override
    public FluentListAssert hasClasses(String... classesToFind) {
        return validateHasClasses("No selected element have classes: ", classesToFind);
    }

    @Override
    public FluentListAssert hasNotClass(String htmlClass) {
        notHasClasses("At least one selected element has class: ", htmlClass);
        return this;
    }

    @Override
    public FluentListAssert hasNotClasses(String... htmlClasses) {
        notHasClasses("At least one selected element has classes: ", htmlClasses);
        return this;
    }

    @Override
    public FluentListAssert hasValue(String value) {
        List<String> actualValues = requiresNonEmpty(actual.values());
        if (!actualValues.contains(value)) {
            failWithMessage("No selected elements have value: " + value
                    + ". Actual values found : " + actualValues);
        }
        return this;
    }

    @Override
    public FluentListAssert hasName(String name) {
        List<String> actualNames = requiresNonEmpty(actual.names());
        if (!actualNames.contains(name)) {
            failWithMessage("No selected elements have name: " + name
                    + ". Actual names found : " + actualNames);
        }
        return this;
    }

    @Override
    public FluentListAssert hasTagName(String tagName) {
        List<String> actualTags = requiresNonEmpty(actual.tagNames());
        if (!actualTags.contains(tagName)) {
            failWithMessage("No selected elements have tag: " + tagName
                    + ". Actual tags found : " + actualTags);
        }
        return this;
    }

    @Override
    public FluentListAssert hasDimension(Dimension dimension) {
        List<Dimension> actualDimensions = requiresNonEmpty(actual.dimensions());
        if (!actualDimensions.contains(dimension)) {
            failWithMessage("No selected elements have dimension: " + dimension.toString()
                    + ". Actual dimensions found : " + actualDimensions.toString());
        }
        return this;
    }

    @Override
    public FluentListAssert hasAttributeValue(String attribute, String value) {
        List<String> actualValues = requiresNonEmpty(actual.attributes(attribute));
        if (!actualValues.contains(value)) {
            failWithMessage("No selected elements have attribute " + attribute
                    + " with value: " + value + ". Actual values found: " + actualValues);
        }
        return this;
    }

    @Override
    public ListAssert<String> hasAttribute(String attribute) {
        List<String> actualValues = requiresNonEmpty(actual.attributes(attribute));
        if (actualValues.stream().allMatch(Objects::isNull)) {
            failWithMessage("No selected element has attribute " + attribute);
        }
        return new ListAssert<>(actualValues);
    }

    @Override
    public FluentListAssert hasNotAttribute(String attribute) {
        List<String> actualValues = requiresNonEmpty(actual.attributes(attribute));
        if (actualValues.stream().anyMatch(Objects::nonNull)) {
            failWithMessage("At least one selected element has attribute " + attribute);
        }
        return this;
    }

    private FluentListAssert validateHasClasses(String message, String... classesToFind) {
        List<String> elementsClasses = requiresNonEmpty(actual.attributes(CLASS_ATTRIBUTE));
        for (String elementClass : elementsClasses) {
            List<String> classesLst = getClasses(elementClass);
            if (classesLst.containsAll(Arrays.asList(classesToFind))) {
                return this;
            }
        }

        String classesFromElement = String.join(", ", elementsClasses);
        failWithMessage(
                message + String.join(", ", classesToFind)
                        + ". Actual classes found : " + classesFromElement);
        return this;
    }

    private void notHasClasses(String message, String... htmlClasses) {
        List<String> elementsClasses = requiresNonEmpty(actual.attributes(CLASS_ATTRIBUTE));
        for (String elementClass : elementsClasses) {
            if (elementClass != null && getClasses(elementClass).containsAll(Arrays.asList(htmlClasses))) {
                failWithMessage(message + Arrays.asList(htmlClasses));
            }
        }
    }

    void failWithMessage(String errorMessage) {
        super.failWithMessage(errorMessage);
    }

    private <T> List<T> requiresNonEmpty(List<T> elements) {
        if (elements.isEmpty()) {
            throw new AssertionError("List is empty. Please make sure you use correct selector.");
        }
        return elements;
    }
}
