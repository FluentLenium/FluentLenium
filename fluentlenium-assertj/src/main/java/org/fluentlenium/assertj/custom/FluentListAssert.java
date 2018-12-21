package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;

import org.fluentlenium.core.domain.FluentList;

import java.util.Arrays;
import java.util.List;

/**
 * Element list assertions.
 */
public class FluentListAssert extends AbstractAssert<FluentListAssert, FluentList> {
    /**
     * Creates a new element list assertion object.
     *
     * @param actual actual element list
     */
    public FluentListAssert(FluentList<?> actual) {
        super(actual, FluentListAssert.class);
    }

    /**
     * check if at least one element of the FluentList contains the text
     *
     * @param textToFind text to find
     * @return assertion object
     */
    public FluentListAssert hasText(String textToFind) {
        List<String> actualTexts = actual.texts();
        for (String text : actualTexts) {
            if (text.contains(textToFind)) {
                return this;
            }
        }
        super.failWithMessage("No selected elements contains text: " + textToFind + " . Actual texts found : " + actualTexts);
        return this;
    }

    /**
     * check if no element of the FluentList contains the text
     *
     * @param textToFind text to find
     * @return assertion object
     */
    public FluentListAssert hasNotText(String textToFind) {
        List<String> actualTexts = actual.texts();
        for (String text : actualTexts) {
            if (text.contains(textToFind)) {
                super.failWithMessage(
                        "At least one selected elements contains text: " + textToFind + " . Actual texts found : " + actualTexts);
            }
        }
        return this;
    }

    /**
     * Check the list size
     *
     * @param expectedSize expected size
     * @return assertion object
     */
    public FluentListAssert hasSize(int expectedSize) {
        int actualSize = actual.size();
        if (actualSize != expectedSize) {
            super.failWithMessage("Expected size: " + expectedSize + ". Actual size: " + actualSize + ".");
        }
        return this;
    }

    /**
     * Check the list size
     *
     * @return List size builder
     */
    public FluentListSizeBuilder hasSize() {
        return new FluentListSizeBuilder(actual.size(), this);
    }

    /**
     * check if an element of the FluentList has the id
     *
     * @param idToFind id to find
     * @return assertion object
     */
    public FluentListAssert hasId(String idToFind) {
        List actualIds = actual.ids();
        if (!actualIds.contains(idToFind)) {
            super.failWithMessage("No selected elements has id: " + idToFind + " . Actual texts found : " + actualIds);
        }
        return this;
    }

    /**
     * check if at least one element of the FluentList contains the text
     *
     * @param classToFind class to find
     * @return assertion object
     */
    public FluentListAssert hasClass(String classToFind) {
        List<String> classes = (List<String>) actual.attributes("class");

        for (String classesStr : classes) {
            List<String> classesLst = Arrays.asList(classesStr.split(" "));
            if (classesLst.contains(classToFind)) {
                return this;
            }
        }

        String classesFromElement = String.join(", ", classes);
        super.failWithMessage(
                "No selected elements has class: " + classToFind + " . Actual classes found : " + classesFromElement);
        return this;
    }

    @Override
    protected void failWithMessage(String errorMessage, Object... arguments) {
        super.failWithMessage(errorMessage);
    }

}
