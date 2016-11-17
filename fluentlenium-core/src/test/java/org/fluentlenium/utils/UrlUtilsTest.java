package org.fluentlenium.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class UrlUtilsTest {
    @Test
    public void testBaseUrlWithoutTrailingSlash() {
        final String test = UrlUtils.concat("http://fluentlenium.org", "abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/abc/def");
    }

    @Test
    public void testBaseUrlWithTrailingSlash() {
        final String test = UrlUtils.concat("http://fluentlenium.org/", "abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/abc/def");
    }

    @Test
    public void testBaseUrlWithPathWithoutTrailingSlash() {
        final String test = UrlUtils.concat("http://fluentlenium.org/path", "abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/path/abc/def");
    }

    @Test
    public void testBaseUrlWithPathWithTrailingSlash() {
        final String test = UrlUtils.concat("http://fluentlenium.org/path/", "abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/path/abc/def");
    }

    @Test
    public void testBaseUrlWithoutTrailingSlashRootPath() {
        final String test = UrlUtils.concat("http://fluentlenium.org", "/abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/abc/def");
    }

    @Test
    public void testBaseUrlWithTrailingSlashRootPath() {
        final String test = UrlUtils.concat("http://fluentlenium.org/", "/abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/abc/def");
    }

    @Test
    public void testBaseUrlWithPathWithoutTrailingSlashRootPath() {
        final String test = UrlUtils.concat("http://fluentlenium.org/path", "/abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/path/abc/def");
    }

    @Test
    public void testBaseUrlWithPathWithTrailingSlashRootPath() {
        final String test = UrlUtils.concat("http://fluentlenium.org/path/", "/abc/def");
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/path/abc/def");
    }

    @Test
    public void testBaseUrlNullUrlDefined() {
        final String test = UrlUtils.concat(null, "/abc/def");
        Assertions.assertThat(test).isEqualTo("/abc/def");
    }

    @Test
    public void testBaseUrlDefinedUrlNull() {
        final String test = UrlUtils.concat("http://fluentlenium.org/path/", null);
        Assertions.assertThat(test).isEqualTo("http://fluentlenium.org/path/");
    }

    @Test
    public void testBaseUrlNullUrlNull() {
        final String test = UrlUtils.concat(null, null);
        Assertions.assertThat(test).isNull();
    }

    @Test
    public void testAbsoluteUrlReplaceBaseUrl() {
        final String test = UrlUtils.concat("http://fluentlenium.org/path/", "http://www.google.fr/test");
        Assertions.assertThat(test).isEqualTo("http://www.google.fr/test");
    }
}
