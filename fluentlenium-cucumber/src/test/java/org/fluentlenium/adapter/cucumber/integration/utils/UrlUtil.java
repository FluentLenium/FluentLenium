package org.fluentlenium.adapter.cucumber.integration.utils;

import java.net.URL;

public final class UrlUtil {

    private UrlUtil() {
        // No instances allowed
    }

    /**
     * Converts a file String to a valid URL String.<br>
     * Example: <code>index.html</code> converts to <code>file://C:/path/to/file/index.html</code>.
     *
     * @param file the file String
     * @return the URL String
     */
    public static String getAbsoluteUrlFromFile(final String file) {
        if (file == null) {
            throw new IllegalArgumentException("file must not be null");
        }

        final URL url = ClassLoader.getSystemResource(file);
        if (url == null) {
            throw new NullPointerException("url from file=" + file + " is null");
        }

        return url.toString();
    }
}
