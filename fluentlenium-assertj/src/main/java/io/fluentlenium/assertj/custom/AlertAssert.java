package io.fluentlenium.assertj.custom;

import io.fluentlenium.core.alert.Alert;
import org.assertj.core.api.AbstractAssert;

/**
 * Default implementation for alert assertions.
 */
public class AlertAssert extends AbstractAssert<AlertAssert, Alert> implements AlertStateAssert {

    public AlertAssert(Alert actual) {
        super(actual, AlertAssert.class);
    }

    @Override
    public AlertStateAssert hasText(String text) {
        isPresent();

        String actualText = actual.getText();

        if (!actualText.contains(text)) {
            failWithActualExpectedAndMessage(actualText, text, "The alert box does not contain the expected text");
        }

        return this;
    }

    @Override
    public AlertStateAssert isPresent() {
        if (!actual.present()) {
            failWithMessage("There is no alert box");
        }
        return this;
    }

    @Override
    public AlertStateAssert isNotPresent() {
        if (actual.present()) {
            failWithMessage("There should be no alert box");
        }
        return this;
    }


}
