package org.fluentlenium.core.events;

import org.openqa.selenium.OutputType;

/**
 * Listen to ScreenshotAs
 */
public interface GetScreenshotAsListener<X> {
    /**
     * Called before or after ScreenshotAs event.
     *
     * @param outputType    output type
     */
    void on(OutputType<X> outputType); // NOPMD ShortMethodName

    /**
     * Called before or after ScreenshotAs event.
     *
     * @param outputType    output type
     */
    void on(OutputType<X> outputType, X x); // NOPMD ShortMethodName
}
