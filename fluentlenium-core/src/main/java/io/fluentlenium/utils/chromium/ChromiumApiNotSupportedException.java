package io.fluentlenium.utils.chromium;

/**
 * Exception for when the chromium api is not supported by browser.
 */
public class ChromiumApiNotSupportedException extends RuntimeException {
    public ChromiumApiNotSupportedException(String message) {
        super(message);
    }

    public ChromiumApiNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}
