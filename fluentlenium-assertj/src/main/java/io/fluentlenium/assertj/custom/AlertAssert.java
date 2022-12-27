package io.fluentlenium.assertj.custom;

import io.fluentlenium.core.alert.AlertImpl;import org.assertj.core.api.AbstractAssert;
import io.fluentlenium.core.alert.AlertImpl;
import org.openqa.selenium.NoAlertPresentException;

/**
 * Default implementation for alert assertions.
 */
public class AlertAssert extends AbstractAssert<AlertAssert, AlertImpl> implements AlertStateAssert {

    public AlertAssert(AlertImpl actual) {
        super(actual, AlertAssert.class);
    }

    @Override
    public AlertStateAssert hasText(String text) {
        try {
            String actualText = actual.getText();
            if (!actualText.contains(text)) {
                failWithMessage(
                        "The alert box does not contain the text: " + text + ". Actual text found : " + actualText);
            }
        } catch (NoAlertPresentException e) {
            failWithMessage("There is no alert box");
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

}
