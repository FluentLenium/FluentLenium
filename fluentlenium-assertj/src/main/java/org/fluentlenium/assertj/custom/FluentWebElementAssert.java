package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.Arrays;
import java.util.List;

/**
 * Element assertions.
 */
public class FluentWebElementAssert extends AbstractAssert<FluentWebElementAssert, FluentWebElement> {

    /**
     * Creates a new element assertions object.
     *
     * @param actual actual element
     */
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
     * @param errorMessage error message
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
        String actualText = actual.text();
        if (!actualText.contains(textToFind)) {
            failWithMessage("The element does not contain the text: " + textToFind + " . Actual text found : " + actualText);
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
        String actualText = actual.text();
        if (!actualText.matches(regexToBeMatched)) {
            failWithMessage(
                    "The element does not match the regex: " + regexToBeMatched + " . Actual text found : " + actualText);
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
        String actualId = actual.id();
        if (!actualId.equals(idToFind)) {
            failWithMessage("The element does not have the id: " + idToFind + " . Actual id found : " + actualId);
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
        String actualClasses = actual.attribute("class");
        if (!getClasses(actualClasses).contains(classToFind)) {
            failWithMessage("The element does not have the class: " + classToFind + " . Actual class found : " + actualClasses);
        }
        return this;
    }

    private List<String> getClasses(String classString) {
        String[] primitiveList = classString.split(" ");
        return Arrays.asList(primitiveList);
    }
}
