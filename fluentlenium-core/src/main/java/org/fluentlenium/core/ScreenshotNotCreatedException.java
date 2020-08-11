package org.fluentlenium.core;

import java.io.IOException;

/**
 * Exception type to be thrown when a screenshot taking process couldn't finish.
 */
public class ScreenshotNotCreatedException extends RuntimeException {

    public ScreenshotNotCreatedException(String errorMessage, IOException e) {
        super(errorMessage, e);
    }
}
