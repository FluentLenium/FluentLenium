package org.fluentlenium.utils;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

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
     * Sanitize base url from current url.
     *
     * @param baseUriSpec
     * @param uriSpec
     * @return
     */
    public static String sanitizeBaseUrl(final String baseUriSpec, final String uriSpec) {
        if (baseUriSpec == null) {
            return null;
        }
        if (uriSpec == null) {
            return baseUriSpec;
        }

        final URI baseUri = URI.create(baseUriSpec);

        if (baseUri.getScheme() != null) {
            return baseUri.toString();
        }

        if (baseUri.getScheme() == null) {
            try {
                String fixedBaseUriSpec = baseUriSpec;
                while (!fixedBaseUriSpec.startsWith("//")) {
                    fixedBaseUriSpec = "/" + fixedBaseUriSpec;
                }

                final URI uri = URI.create(uriSpec);

                final URI withSchemeBaseUri = new URIBuilder(fixedBaseUriSpec).setScheme(uri.getScheme()).build();
                if (withSchemeBaseUri.getAuthority().equals(uri.getAuthority())) {
                    return withSchemeBaseUri.toString();
                }
            } catch (final URISyntaxException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }

        return baseUri.toString();
    }

}
