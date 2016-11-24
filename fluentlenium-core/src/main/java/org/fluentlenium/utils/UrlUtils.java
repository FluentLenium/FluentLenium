package org.fluentlenium.utils;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
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
     * @return
     */
    public static String sanitizeBaseUrl(final String baseUriSpec, final String uriSpec) {
        if (baseUriSpec == null) {
            return null;
        }

        URI baseUri = URI.create(baseUriSpec);

        try {
            baseUri = ensureScheme(baseUri, "http");

            final URI uri = uriSpec == null ? null : URI.create(uriSpec);
            final String scheme = uri == null || !Objects.equals(baseUri.getAuthority(), uri.getAuthority()) || !Arrays
                    .asList(new String[] {"http", "https"}).contains(uri.getScheme()) ? baseUri.getScheme() : uri.getScheme();

            if (!scheme.equals(baseUri.getScheme())) {
                return new URIBuilder(baseUri).setScheme(scheme).build().toString();
            }
        } catch (final URISyntaxException e) {
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
     * @throws URISyntaxException
     */
    private static URI ensureScheme(final URI uri, final String defaultScheme) throws URISyntaxException {
        String fixedBaseUriSpec = uri.toString();
        if (uri.getScheme() == null) {
            while (!fixedBaseUriSpec.startsWith("//")) {
                fixedBaseUriSpec = "/" + fixedBaseUriSpec;
            }
            return new URIBuilder(fixedBaseUriSpec).setScheme(defaultScheme).build();
        }
        return uri;
    }

}
