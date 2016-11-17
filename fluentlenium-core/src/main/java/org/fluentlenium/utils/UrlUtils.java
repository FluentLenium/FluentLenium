package org.fluentlenium.utils;

import java.net.URI;

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

}
