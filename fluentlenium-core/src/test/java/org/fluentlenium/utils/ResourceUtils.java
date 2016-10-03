package org.fluentlenium.utils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Utility class to access classpath resource
 */
public final class ResourceUtils {
    private ResourceUtils() {
        // Utility class
    }

    public static File getResourceAsFile(final String resourcePath) throws IOException {
        InputStream resourceStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
        if (resourceStream == null) {
            return null;
        }

        File tempFile = File.createTempFile(String.valueOf(resourceStream.hashCode()), ".tmp");
        tempFile.deleteOnExit();

        FileOutputStream out = new FileOutputStream(tempFile);
        try {
            IOUtils.copy(resourceStream, out);
        } finally {
            IOUtils.closeQuietly(out);
        }

        return tempFile;
    }

    public static URL getResourceAsURL(final String resourcePath) throws IOException {
        File resourceAsFile = getResourceAsFile(resourcePath);
        if (resourceAsFile == null) {
            return null;
        }
        return resourceAsFile.toURI().toURL();
    }
}
