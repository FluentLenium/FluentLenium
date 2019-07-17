package org.fluentlenium.core;

import java.io.IOException;

public class ScreenshotNotCreatedException extends RuntimeException {

    public ScreenshotNotCreatedException(String errorMessage, IOException e) {
        super(errorMessage, e);
    }
}
