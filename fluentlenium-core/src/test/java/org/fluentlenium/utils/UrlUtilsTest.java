package org.fluentlenium.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class UrlUtilsTest {
    @Test
    public void testBaseUrlWithoutTrailingSlash() {
        String test = UrlUtils.concat("http://fluentlenium.org", "abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/abc/def");
    }

    @Test
    public void testBaseUrlWithTrailingSlash() {
        String test = UrlUtils.concat("http://fluentlenium.org/", "abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/abc/def");
    }

    @Test
    public void testBaseUrlWithPathWithoutTrailingSlash() {
        String test = UrlUtils.concat("http://fluentlenium.org/path", "abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/path/abc/def");
    }

    @Test
    public void testBaseUrlWithPathWithTrailingSlash() {
        String test = UrlUtils.concat("http://fluentlenium.org/path/", "abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/path/abc/def");
    }

    @Test
    public void testBaseUrlWithoutTrailingSlashRootPath() {
        String test = UrlUtils.concat("http://fluentlenium.org", "/abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/abc/def");
    }

    @Test
    public void testBaseUrlWithTrailingSlashRootPath() {
        String test = UrlUtils.concat("http://fluentlenium.org/", "/abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/abc/def");
    }

    @Test
    public void testBaseUrlWithPathWithoutTrailingSlashRootPath() {
        String test = UrlUtils.concat("http://fluentlenium.org/path", "/abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/path/abc/def");
    }

    @Test
    public void testBaseUrlWithPathWithTrailingSlashRootPath() {
        String test = UrlUtils.concat("http://fluentlenium.org/path/", "/abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/path/abc/def");
    }

    @Test
    public void testBaseUrlNullUrlDefined() {
        String test = UrlUtils.concat(null, "/abc/def");
        Assertions.assertThat(test).isEqualTo("/abc/def");
    }

    @Test
    public void testBaseUrlDefinedUrlNull() {
        String test = UrlUtils.concat("http://fluentlenium.org/path/", null);
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/path/");
    }

    @Test
    public void testBaseUrlNullUrlNull() {
        String test = UrlUtils.concat(null, null);
        Assertions.assertThat(test).isNull();
    }

    @Test
    public void testAbsoluteUrlReplaceBaseUrl() {
        String test = UrlUtils.concat("http://fluentlenium.org/path/", "http://www.google.fr/test");
        Assertions.assertThat(test).isEqualTo("http://www.google.fr/test");
    }

    @Test
    public void testSanitizeBaseUrl() {
        String baseUrl = UrlUtils.sanitizeBaseUrl("http://fluentlenium.org/path/", "https://fluentlenium.org/path/abc");
        Assertions.assertThat(baseUrl).isEqualTo("https://fluentlenium.org/path/");
    }

    @Test
    public void testSanitizeBaseUrlOtherDomain() {
        String baseUrl = UrlUtils.sanitizeBaseUrl("http://fluentlenium.org/path/", "https://www.google.com/path/abc");
        Assertions.assertThat(baseUrl).isEqualTo("http://fluentlenium.org/path/");
    }

    @Test
    public void testSanitizeBaseUrlNull() {
        String baseUrl = UrlUtils.sanitizeBaseUrl(null, "https://www.google.com/path/abc");
        Assertions.assertThat(baseUrl).isNull();
    }

    @Test
    public void testSanitizeBaseUrlMissingScheme() {
        String baseUrl = UrlUtils.sanitizeBaseUrl("fluentlenium.org/path/", "https://fluentlenium.org/path/abc");
        Assertions.assertThat(baseUrl).isEqualTo("https://fluentlenium.org/path/");
    }

    @Test
    public void testSanitizeBaseUrlMissingSchemeOtherDomain() {
        String baseUrl = UrlUtils.sanitizeBaseUrl("fluentlenium.org/path/", "https://www.google.com/path/abc");
        Assertions.assertThat(baseUrl).isEqualTo("http://fluentlenium.org/path/");
    }
}
