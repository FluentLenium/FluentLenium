package org.fluentlenium.core;

public interface FluentDriverConfigurationReader {
    enum TriggerMode {ON_FAIL, NEVER}

    String getScreenshotPath();

    String getHtmlDumpPath();

    TriggerMode getScreenshotMode();

    TriggerMode getHtmlDumpMode();
}
