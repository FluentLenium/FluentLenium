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

    /**
     * Get a classpath resource as File.
     *
     * @param resourcePath resource classpath
     * @return File matching the classpath resource, or null if not found
     * @throws IOException when an exception occurs while reading the resource
     */
    public static File getResourceAsFile(final String resourcePath) throws IOException {
        final InputStream resourceStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
        if (resourceStream == null) {
            return null;
        }

        final File tempFile = File.createTempFile(String.valueOf(resourceStream.hashCode()), ".tmp");
        tempFile.deleteOnExit();

        final FileOutputStream out = new FileOutputStream(tempFile);
        try {
            IOUtils.copy(resourceStream, out);
        } finally {
            IOUtils.closeQuietly(out);
        }

        return tempFile;
    }

    /**
     * Get a classpath resource as File URL.
     *
     * @param resourcePath resource classpath
     * @return File matching the classpath resource, or null if not found
     * @throws IOException when an exception occurs while reading the resource
     */
    public static URL getResourceAsURL(final String resourcePath) throws IOException {
        final File resourceAsFile = getResourceAsFile(resourcePath);
        if (resourceAsFile == null) {
            return null;
        }
        return resourceAsFile.toURI().toURL();
    }
}
