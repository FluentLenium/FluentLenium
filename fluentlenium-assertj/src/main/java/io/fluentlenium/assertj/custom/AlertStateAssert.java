package io.fluentlenium.assertj.custom;

/**
 * Interface for asserting the state of alert boxes.
 */
public interface AlertStateAssert {

    /**
     * Check that the alert box contains the given text.
     * <p>
     * It fails assertion when
     * <ul>
     * <li>there is an alert box but the text in it doesn't contain the expected text,</li>
     * <li>there is no alert box at all.</li>
     * </ul>
     *
     * @param text text to search for
     * @return this assertion object
     */
    AlertStateAssert hasText(String text);

    /**
     * Check that an alert box is present.
     *
     * @return this assertion object
     */
    AlertStateAssert isPresent();
}
