package io.fluentlenium.utils;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

/**
 * Utils class for Url manipulation.
 */
public final class UrlUtils {

    private static final String PATH_SEPARATOR = "/";
    private static final String HTTP = "http";
    private static final String HTTPS = "https";

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
        if (baseUriSpec != null && !baseUriSpec.endsWith(PATH_SEPARATOR)) {
            baseUriSpec = baseUriSpec + PATH_SEPARATOR;
        }
        URI baseUri = uriFromSpec(baseUriSpec);

        if (baseUri != null && uriSpec != null && uriSpec.startsWith(PATH_SEPARATOR)) {
            uriSpec = uriSpec.substring(1);
        }

        URI uri = uriFromSpec(uriSpec);

        if (baseUri != null) {
            return uri == null ? baseUri.toString() : baseUri.resolve(uri).toString();
        } else if (uri != null) {
            return uri.toString();
        } else {
            return null;
        }
    }

    private static URI uriFromSpec(String uriSpec) {
        return uriSpec == null ? null : URI.create(uriSpec);
    }

    /**
     * Sanitize base url from current url by using the same scheme if http/https.
     *
     * @param baseUriSpec base URI
     * @param uriSpec     current URI
     * @return Sanitized base url
     */
    public static String sanitizeBaseUrl(String baseUriSpec, String uriSpec) {
        if (baseUriSpec != null) {
            URI baseUri = URI.create(baseUriSpec);

            try {
                baseUri = ensureScheme(baseUri, HTTP);

                URI uri = uriFromSpec(uriSpec);
                String scheme = uri == null
                        || !Objects.equals(baseUri.getAuthority(), uri.getAuthority())
                        || !Arrays.asList(new String[]{HTTP, HTTPS}).contains(uri.getScheme())
                        ? baseUri.getScheme()
                        : uri.getScheme();

                if (!scheme.equals(baseUri.getScheme())) {
                    return new URIBuilder(baseUri).setScheme(scheme).build().toString();
                }
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }

            return baseUri.toString();
        }
        return null;
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
        Preconditions.checkArgument(file, "file must not be null");
        URL url = ClassLoader.getSystemResource(file);
        Preconditions.checkArgument(url, "url from file=" + file + " is null");
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
