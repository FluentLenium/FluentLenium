package org.fluentlenium.utils;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

/**
 * Utils class for Url manipulation.
 */
public final class UrlUtils { // NOPMD CyclomaticComplexity
    private UrlUtils() {
        // Utility class
    }

    /**
     * Concatenate 2 URL Strings.
     *
     * @param baseUriSpec base url
     * @param uriSpec     path part
     * @return Concat URL
     */
    public static String concat(String baseUriSpec, String uriSpec) { // NOPMD CyclomaticComplexity NPathComplexity
        if (baseUriSpec != null && !baseUriSpec.endsWith("/")) {
            baseUriSpec = baseUriSpec + "/";
        }
        URI baseUri = null;
        if (baseUriSpec != null) {
            baseUri = URI.create(baseUriSpec);
        }

        if (baseUri != null && uriSpec != null && uriSpec.startsWith("/")) {
            uriSpec = uriSpec.substring(1);
        }

        URI uri = null;
        if (uriSpec != null) {
            uri = URI.create(uriSpec);
        }

        if (baseUri != null && uri != null) { // NOPMD ConfusingTernary
            return baseUri.resolve(uri).toString();
        } else if (baseUri != null) { // NOPMD ConfusingTernary
            return baseUri.toString();
        } else if (uri != null) { // NOPMD ConfusingTernary
            return uri.toString();
        } else {
            return null;
        }
    }

    /**
     * Sanitize base url from current url by using the same scheme if http/https.
     *
     * @param baseUriSpec base URI
     * @param uriSpec     current URI
     * @return Sanitized base url
     */
    public static String sanitizeBaseUrl(String baseUriSpec, String uriSpec) {
        if (baseUriSpec == null) {
            return null;
        }

        URI baseUri = URI.create(baseUriSpec);

        try {
            baseUri = ensureScheme(baseUri, "http");

            URI uri = uriSpec == null ? null : URI.create(uriSpec);
            String scheme = uri == null || !Objects.equals(baseUri.getAuthority(), uri.getAuthority()) || !Arrays
                    .asList(new String[] {"http", "https"}).contains(uri.getScheme()) ? baseUri.getScheme() : uri.getScheme();

            if (!scheme.equals(baseUri.getScheme())) {
                return new URIBuilder(baseUri).setScheme(scheme).build().toString();
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        return baseUri.toString();
    }

    /**
     * Ensure a scheme is defined on the URI.
     *
     * @param uri           uri
     * @param defaultScheme scheme to use when no scheme is defined on uri
     * @return uri with scheme defined
     * @throws URISyntaxException if the URI string to build is not a valid URI
     */
    private static URI ensureScheme(URI uri, String defaultScheme) throws URISyntaxException {
        URI uriWithScheme = uri;
        String fixedBaseUriSpec = uri.toString();
        if (uri.getScheme() == null) {
            while (!fixedBaseUriSpec.startsWith("//")) {
                fixedBaseUriSpec = PATH_SEPARATOR + fixedBaseUriSpec;
            }
            uriWithScheme = new URIBuilder(fixedBaseUriSpec).setScheme(defaultScheme).build();
        }
        return uriWithScheme;
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
            throw new IllegalArgumentException("url from file=" + file + " is null");
        }

        return url.toString();
    }

    /**
     * Removes file name from URL string
     *
     * @param file the file String
     * @return the URL String
     */
    public static String getAbsoluteUrlPathFromFile(String file) {
        String url = getAbsoluteUrlFromFile(file);
        return url.substring(0, url.lastIndexOf('/'));
    }

}
