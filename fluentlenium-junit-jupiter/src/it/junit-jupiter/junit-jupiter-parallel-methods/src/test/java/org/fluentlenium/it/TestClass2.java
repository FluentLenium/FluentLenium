package org.fluentlenium.it;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class TestClass2 extends FluentIntegTest {

    private static final String LOCAL_FILE_PATH = "inputs.html";
    private static final String SELECTOR = "input";

    @Test
    public void testMethod5() {
        goTo(UrlUtil.getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("5");
        await().until(el(SELECTOR)).value().equalTo("5");
        assertThat(el(SELECTOR).value()).isEqualTo("5");
    }

    @Test
    public void testMethod6() {
        goTo(UrlUtil.getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("6");
        await().until(el(SELECTOR)).value().equalTo("6");
        assertThat(el(SELECTOR).value()).isEqualTo("6");
    }

    @Test
    public void testMethod7() {
        goTo(UrlUtil.getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("7");
        await().until(el(SELECTOR)).value().equalTo("7");
        assertThat(el(SELECTOR).value()).isEqualTo("7");
    }

    @Test
    public void testMethod8() {
        goTo(UrlUtil.getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("8");
        await().until(el(SELECTOR)).value().equalTo("8");
        assertThat(el(SELECTOR).value()).isEqualTo("8");
    }
}
