package org.fluentlenium.utils.chromium;

/**
 * Exception for when the chromium api is not supported by browser.
 */
public class ChromiumApiNotSupportedException extends RuntimeException {
    ChromiumApiNotSupportedException(String message) {
        super(message);
    }
}
