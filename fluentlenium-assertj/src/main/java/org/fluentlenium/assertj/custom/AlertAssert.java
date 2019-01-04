package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;
import org.fluentlenium.core.alert.AlertImpl;
import org.openqa.selenium.NoAlertPresentException;

/**
 * Alert assertions.
 */
public class AlertAssert extends AbstractAssert<AlertAssert, AlertImpl> {

    /**
     * Creates a new assertion object for alert.
     *
     * @param actual actual alert
     */
    public AlertAssert(AlertImpl actual) {
        super(actual, AlertAssert.class);
    }

    /**
     * Check that the alert box contains the given text
     *
     * @param text text to search for
     * @return self
     */
    public AlertAssert hasText(String text) {
        try {
            String actualText = actual.getText();
            if (!actualText.contains(text)) {
                super.failWithMessage(
                        "The alert box does not contain the text: " + text + ". Actual text found : " + actualText);
            }
        } catch (NoAlertPresentException e) {
            super.failWithMessage("There is no alert box");
        }

        return this;
    }

    /**
     * Check that an alert box is present
     *
     * @return self
     */
    public AlertAssert isPresent() {
        if (!actual.present()) {
            super.failWithMessage("There is no alert box");
        }
        return this;
    }

}
