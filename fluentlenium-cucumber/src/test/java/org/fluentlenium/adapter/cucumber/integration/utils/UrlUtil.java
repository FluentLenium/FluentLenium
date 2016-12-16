package org.fluentlenium.adapter.cucumber.integration.utils;

import java.net.URL;

/**
 * Utility class for Url.
 */
public final class UrlUtil {

    private UrlUtil() {
        // Utility class
    }

    /**
     * Converts a file String to a valid URL String.<br>
     * Example: <code>index.html</code> converts to <code>file://C:/path/to/file/index.html</code>.
     *
     * @param file the file String
     * @return the URL String
     */
    public static String getAbsoluteUrlFromFile(String file) {
        if (file == null) {
            throw new IllegalArgumentException("file must not be null");
        }

        URL url = ClassLoader.getSystemResource(file);
        if (url == null) {
            throw new NullPointerException("url from file=" + file + " is null");
        }

        return url.toString();
    }
}
