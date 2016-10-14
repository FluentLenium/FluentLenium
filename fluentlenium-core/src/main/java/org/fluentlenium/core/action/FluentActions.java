package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebElement;

/**
 * All actions that can be used on the list or on a web element.
 *
 * @param <T> {@code this} object type to chain method calls
 * @param <E> type of fluent web element
 */
public interface FluentActions<T, E extends FluentWebElement> {
    /**
     * Perform a click.
     *
     * @return this object reference to chain methods calls
     * @see WebElement#click()
     */
    T click();

    /**
     * Perform a double click.
     *
     * @return this object reference to chain methods calls
     */
    T doubleClick();

    /**
     * Perform a context click.
     *
     * @return this object reference to chain methods calls
     */
    T contextClick();

    /**
     * Perform a form submission.
     *
     * @return this object reference to chain methods calls.
     * @see WebElement#submit()
     */
    T submit();

    /**
     * Write text in the element.
     *
     * @param text one or many text to send.
     * @return this object reference to chain methods calls.
     * @see WebElement#sendKeys(CharSequence...)
     */
    T write(String... text);

    /**
     * Construct a Fill Builder in order to allow easy fill of visible input fields.
     *
     * @return Fill builder
     */
    Fill<E> fill();

    /**
     * Construct a Fill Select Builder in order to allow easy fill of visible input fields.
     *
     * @return Fill select builder
     */
    FillSelect<E> fillSelect();

    /**
     * Select a frame using this element.
     *
     * @return this object reference to chain methods calls.
     */
    T frame();
}
