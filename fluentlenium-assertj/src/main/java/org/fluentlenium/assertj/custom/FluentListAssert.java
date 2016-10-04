package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;
import org.fluentlenium.core.domain.FluentList;

import java.util.Arrays;
import java.util.List;

public class FluentListAssert extends AbstractAssert<FluentListAssert, FluentList> {
    public FluentListAssert(final FluentList<?> actual) {
        super(actual, FluentListAssert.class);
    }

    /**
     * check if at least one element of the FluentList contains the text
     *
     * @param textToFind text to find
     * @return assertion object
     */
    public FluentListAssert hasText(final String textToFind) {
        final List<String> actualTexts = actual.texts();
        for (final String text : actualTexts) {
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
    public FluentListAssert hasNotText(final String textToFind) {
        final List<String> actualTexts = actual.texts();
        for (final String text : actualTexts) {
            if (text.contains(textToFind)) {
                super.failWithMessage(
                        "At least one selected elements contains text: " + textToFind + " . Actual texts found : " + actualTexts);
            }
        }
        return this;
    }

    public FluentListAssert hasSize(final int expectedSize) {
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
    public FluentListAssert hasId(final String idToFind) {
        final List actualIds = actual.ids();
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
    public FluentListAssert hasClass(final String classToFind) {
        final List<String> classes = (List<String>) actual.attributes("class");

        for (final String classesStr : classes) {
            final List<String> classesLst = Arrays.asList(classesStr.split(" "));
            if (classesLst.contains(classToFind)) {
                return this;
            }
        }

        final StringBuilder builder = new StringBuilder();
        for (final String classFromElement : classes) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(classFromElement);
        }

        super.failWithMessage(
                "No selected elements has class: " + classToFind + " . Actual classes found : " + builder.toString());
        return this;
    }

    /*
     * Used in FluentListSizeBuilder to raise AssertionError
     */
    /* default */ void internalFail(final String reason) {
        super.failWithMessage(reason);
    }

}
