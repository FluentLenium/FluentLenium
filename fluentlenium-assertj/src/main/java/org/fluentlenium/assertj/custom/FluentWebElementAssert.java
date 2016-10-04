package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.Arrays;
import java.util.List;

public class FluentWebElementAssert extends AbstractAssert<FluentWebElementAssert, FluentWebElement> {

    public FluentWebElementAssert(FluentWebElement actual) {
        super(actual, FluentWebElementAssert.class);
    }

    private void failIsEnabled() {
        failWithMessage("Object not enabled");
    }

    private void failIsNotEnabled() {
        failWithMessage("Object is enabled");
    }

    /**
     * check if the element is enabled
     *
     * @return {@code this} assertion object.
     */
    public FluentWebElementAssert isEnabled() {
        if (!actual.enabled()) {
            failIsEnabled();
        }
        return this;
    }

    /**
     * check if the element is not enabled
     *
     * @return {@code this} assertion object.
     */
    public FluentWebElementAssert isNotEnabled() {
        if (actual.enabled()) {
            failIsNotEnabled();
        }
        return this;
    }

    /**
     * check if the element is displayed
     *
     * @return {@code this} assertion object.
     */
    public FluentWebElementAssert isDisplayed() {
        if (!actual.displayed()) {
            failIsNotDisplayed();
        }

        return this;
    }

    /**
     * check if the element is not displayed
     *
     * @return {@code this} assertion object.
     */
    public FluentWebElementAssert isNotDisplayed() {
        if (actual.displayed()) {
            failIsDisplayed();
        }
        return this;
    }

    private void failIsDisplayed() {
        failWithMessage("Object not displayed");
    }

    private void failIsNotDisplayed() {
        failWithMessage("Object is displayed");
    }

    /**
     * check if the element is selected
     *
     * @return {@code this} assertion object.
     */
    public FluentWebElementAssert isSelected() {
        if (!actual.selected()) {
            failIsSelected();
        }
        return this;
    }

    /**
     * check if the element is not selected
     *
     * @return {@code this} assertion object.
     */
    public FluentWebElementAssert isNotSelected() {
        if (actual.selected()) {
            failIsNotSelected();
        }
        return this;
    }

    /**
     * Secure failWithMessage by escaping String.format tokens when called without arguments.
     *
     * @see #failWithMessage(String, Object...)
     */
    protected void failWithMessage(String errorMessage) {
        super.failWithMessage(errorMessage.replaceAll("(?:[^%]|\\A)%(?:[^%]|\\z)", "%%"));
    }

    /**
     * check if the element contains the text
     *
     * @param textToFind text to find
     * @return {@code this} assertion object.
     */
    public FluentWebElementAssert hasText(String textToFind) {
        if (!actual.text().contains(textToFind)) {
            failWithMessage("The element does not contain the text: " + textToFind + " . Actual text found : " + actual.text());
        }

        return this;
    }

    /**
     * check if the element matches the given regex
     *
     * @param regexToBeMatched regex to be matched
     * @return {@code this} assertion object.
     */
    public FluentWebElementAssert hasTextMatching(String regexToBeMatched) {
        if (!actual.text().matches(regexToBeMatched)) {
            failWithMessage(
                    "The element does not match the regex: " + regexToBeMatched + " . Actual text found : " + actual.text());

        }

        return this;
    }

    /**
     * check if the element does not contain the text
     *
     * @param textToFind text to find
     * @return {@code this} assertion object.
     */
    public FluentWebElementAssert hasNotText(String textToFind) {
        if (actual.text().contains(textToFind)) {
            failWithMessage("The element contain the text: " + textToFind);
        }

        return this;
    }

    private void failIsSelected() {
        failWithMessage("Object not selected");
    }

    private void failIsNotSelected() {
        failWithMessage("Object is selected");
    }

    /**
     * check if the element has the given id
     *
     * @param idToFind id to find
     * @return {@code this} assertion object.
     */
    public FluentWebElementAssert hasId(String idToFind) {
        if (!actual.id().equals(idToFind)) {
            failWithMessage("The element does not have the id: " + idToFind + " . Actual id found : " + actual.id());
        }
        return this;
    }

    /**
     * check if the element has the class
     *
     * @param classToFind class to find
     * @return {@code this} assertion object.
     */
    public FluentWebElementAssert hasClass(String classToFind) {
        if (!getClasses().contains(classToFind)) {
            failWithMessage("The element does not have the class: " + classToFind + " . Actual class found : " + actual
                    .attribute("class"));
        }
        return this;
    }

    private List<String> getClasses() {
        final String[] primitiveList = actual.attribute("class").split(" ");
        return Arrays.asList(primitiveList);
    }
}
