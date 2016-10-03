package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;
import org.fluentlenium.core.alert.Alert;
import org.openqa.selenium.NoAlertPresentException;

public class AlertAssert extends AbstractAssert<AlertAssert, Alert> {

    public AlertAssert(Alert actual) {
        super(actual, AlertAssert.class);
    }

    /**
     * Check that the alert box contains the given text
     *
     * @return self
     */
    public AlertAssert hasText(String textToFind) {
        try {
            if (!actual.getText().contains(textToFind)) {
                super.failWithMessage(
                        "The alert box does not contain the text: " + textToFind + " . Actual text found : " + actual.getText());
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
        try {
            actual.switchTo();
        } catch (NoAlertPresentException e) {
            super.failWithMessage("There is no alert box");
        }

        return this;
    }

}
