package org.fluentlenium.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Unit test for {@link UrlUtils}.
 */
public class UrlUtilsTest {
    @Test
    public void testBaseUrlWithoutTrailingSlash() {
        String test = UrlUtils.concat("http://fluentlenium.io", "abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.io/abc/def");
    }

    @Test
    public void testBaseUrlWithTrailingSlash() {
        String test = UrlUtils.concat("http://fluentlenium.io/", "abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.io/abc/def");
    }

    @Test
    public void testBaseUrlWithPathWithoutTrailingSlash() {
        String test = UrlUtils.concat("http://fluentlenium.io/path", "abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.io/path/abc/def");
    }

    @Test
    public void testBaseUrlWithPathWithTrailingSlash() {
        String test = UrlUtils.concat("http://fluentlenium.io/path/", "abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.io/path/abc/def");
    }

    @Test
    public void testBaseUrlWithoutTrailingSlashRootPath() {
        String test = UrlUtils.concat("http://fluentlenium.io", "/abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.io/abc/def");
    }

    @Test
    public void testBaseUrlWithTrailingSlashRootPath() {
        String test = UrlUtils.concat("http://fluentlenium.io/", "/abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.io/abc/def");
    }

    @Test
    public void testBaseUrlWithPathWithoutTrailingSlashRootPath() {
        String test = UrlUtils.concat("http://fluentlenium.io/path", "/abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.io/path/abc/def");
    }

    @Test
    public void testBaseUrlWithPathWithTrailingSlashRootPath() {
        String test = UrlUtils.concat("http://fluentlenium.io/path/", "/abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.io/path/abc/def");
    }

    @Test
    public void testBaseUrlNullUrlDefined() {
        String test = UrlUtils.concat(null, "/abc/def");
        Assertions.assertThat(test).isEqualTo("/abc/def");
    }

    @Test
    public void testBaseUrlDefinedUrlNull() {
        String test = UrlUtils.concat("http://fluentlenium.io/path/", null);
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.io/path/");
    }

    @Test
    public void testBaseUrlNullUrlNull() {
        String test = UrlUtils.concat(null, null);
        Assertions.assertThat(test).isNull();
    }

    @Test
    public void testAbsoluteUrlReplaceBaseUrl() {
        String test = UrlUtils.concat("http://fluentlenium.io/path/", "http://www.google.fr/test");
        Assertions.assertThat(test).isEqualTo("http://www.google.fr/test");
    }

    @Test
    public void testSanitizeBaseUrl() {
        String baseUrl = UrlUtils.sanitizeBaseUrl("http://fluentlenium.io/path/", "https://fluentlenium.io/path/abc");
        Assertions.assertThat(baseUrl).isEqualTo("https://fluentlenium.io/path/");
    }

    @Test
    public void testSanitizeBaseUrlOtherDomain() {
        String baseUrl = UrlUtils.sanitizeBaseUrl("http://fluentlenium.io/path/", "https://www.google.com/path/abc");
        Assertions.assertThat(baseUrl).isEqualTo("http://fluentlenium.io/path/");
    }

    @Test
    public void testSanitizeBaseUrlNull() {
        String baseUrl = UrlUtils.sanitizeBaseUrl(null, "https://www.google.com/path/abc");
        Assertions.assertThat(baseUrl).isNull();
    }

    @Test
    public void testSanitizeBaseUrlMissingScheme() {
        String baseUrl = UrlUtils.sanitizeBaseUrl("fluentlenium.io/path/", "https://fluentlenium.io/path/abc");
        Assertions.assertThat(baseUrl).isEqualTo("https://fluentlenium.io/path/");
    }

    @Test
    public void testSanitizeBaseUrlMissingSchemeOtherDomain() {
        String baseUrl = UrlUtils.sanitizeBaseUrl("fluentlenium.io/path/", "https://www.google.com/path/abc");
        Assertions.assertThat(baseUrl).isEqualTo("http://fluentlenium.io/path/");
    }
}
