package org.fluentlenium.assertj.custom;

public interface AlertStateAssert {

    /**
     * Check that the alert box contains the given text
     *
     * @param text text to search for
     * @return self
     */
    AlertStateAssert hasText(String text);

    /**
     * Check that an alert box is present
     *
     * @return self
     */
    AlertStateAssert isPresent();
}
