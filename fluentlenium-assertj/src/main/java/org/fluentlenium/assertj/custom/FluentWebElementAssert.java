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
        super.failWithMessage("Object not enabled");
    }

    private void failIsNotEnabled() {
        super.failWithMessage("Object is enabled");
    }

    /**
     * check if the element is enabled
     *
     * @return
     */
    public FluentWebElementAssert isEnabled() {
        if (!actual.isEnabled()) {
            failIsEnabled();
        }
        return this;
    }

    /**
     * check if the element is not enabled
     *
     * @return
     */
    public FluentWebElementAssert isNotEnabled() {
        if (actual.isEnabled()) {
            failIsNotEnabled();
        }
        return this;
    }

    /**
     * check if the element is displayed
     *
     * @return
     */
    public FluentWebElementAssert isDisplayed() {
        if (!actual.isDisplayed()) {
            failIsNotDisplayed();
        }

        return this;
    }

    /**
     * check if the element is not displayed
     *
     * @return
     */
    public FluentWebElementAssert isNotDisplayed() {
        if (actual.isDisplayed()) {
            failIsDisplayed();
        }
        return this;
    }

    private void failIsDisplayed() {
        super.failWithMessage("Object not displayed");
    }

    private void failIsNotDisplayed() {
        super.failWithMessage("Object is displayed");
    }

    /**
     * check if the element is selected
     *
     * @return
     */
    public FluentWebElementAssert isSelected() {
        if (!actual.isSelected()) {
            failIsSelected();
        }
        return this;
    }

    /**
     * check if the element is not selected
     *
     * @return
     */
    public FluentWebElementAssert isNotSelected() {
        if (actual.isSelected()) {
            failIsNotSelected();
        }
        return this;
    }

    /**
     * check if the element contains the text
     *
     * @return
     */
    public FluentWebElementAssert hasText(String textToFind) {
        if (!actual.getText().contains(textToFind)) {
            super.failWithMessage("The element does not contain the text: " + textToFind + " . Actual text found : " + actual.getText());

        }

        return this;
    }

    /**
     * check if the element matches the given regex
     *
     * @return
     */
    public FluentWebElementAssert hasTextMatching(String regexToBeMatched) {
        if (!actual.getText().matches(regexToBeMatched)) {
            super.failWithMessage("The element does not match the regex: " + regexToBeMatched + " . Actual text found : " + actual.getText());

        }

        return this;
    }

    /**
     * check if the element does not contain the text
     *
     * @return
     */
    public FluentWebElementAssert hasNotText(String textToFind) {
        if (actual.getText().contains(textToFind)) {
            super.failWithMessage("The element contain the text: " + textToFind);
        }

        return this;
    }

    private void failIsSelected() {
        super.failWithMessage("Object not selected");
    }

    private void failIsNotSelected() {
        super.failWithMessage("Object is selected");
    }

    /**
     * check if the element has the given id
     *
     * @param id to check
     * @return
     */
    public FluentWebElementAssert hasId(String id) {
        if (!actual.getId().equals(id)) {
            super.failWithMessage("The element does not have the id: " + id + " . Actual id found : " + actual.getId());
        }
        return this;
    }

    /**
     * check if the element has the class
     *
     * @param classToFind
     * @return
     */
    public FluentWebElementAssert hasClass(String classToFind) {
        if (!getClasses().contains(classToFind)) {
            super.failWithMessage("The element does not have the class: " + classToFind + " . Actual class found : " + actual.getAttribute("class"));
        }
        return this;
    }

    private List<String> getClasses() {
        final String[] primitiveList = actual.getAttribute("class").split(" ");
        return Arrays.asList(primitiveList);
    }
}
