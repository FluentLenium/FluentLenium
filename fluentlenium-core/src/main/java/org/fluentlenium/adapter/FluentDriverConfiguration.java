package org.fluentlenium.adapter;

import org.fluentlenium.core.FluentDriverConfigurationReader;

public interface FluentDriverConfiguration extends FluentDriverConfigurationReader {
    void setScreenshotPath(String path);

    void setHtmlDumpPath(String htmlDumpPath);

    void setScreenshotMode(TriggerMode mode);

    void setHtmlDumpMode(TriggerMode htmlDumpMode);
}
