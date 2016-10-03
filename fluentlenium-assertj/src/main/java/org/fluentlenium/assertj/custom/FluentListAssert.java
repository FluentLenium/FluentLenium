package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;
import org.fluentlenium.core.domain.FluentList;

import java.util.Arrays;
import java.util.List;

public class FluentListAssert extends AbstractAssert<FluentListAssert, FluentList> {
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
     * check if at no element of the FluentList contains the text
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

    public FluentListAssert hasSize(int expectedSize) {
        if (actual.size() != expectedSize) {
            super.failWithMessage("Expected size: " + expectedSize + ". Actual size: " + actual.size() + ".");
        }
        return this;
    }

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
        List<String> classesFromElements = (List<String>) actual.attributes("class");

        for (String classesStr : classesFromElements) {
            List<String> classesLst = Arrays.asList(classesStr.split(" "));
            if (classesLst.contains(classToFind)) {
                return this;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String classFromElement : classesFromElements) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(classFromElement);
        }

        super.failWithMessage("No selected elements has class: " + classToFind + " . Actual classes found : " + sb.toString());
        return this;
    }

    /*
     * Used in FluentListSizeBuilder to raise AssertionError
     */
    void internalFail(String reason) {
        super.failWithMessage(reason);
    }

}
