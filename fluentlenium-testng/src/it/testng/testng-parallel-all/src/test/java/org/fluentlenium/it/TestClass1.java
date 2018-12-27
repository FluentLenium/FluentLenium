package org.fluentlenium.it;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class TestClass1 extends FluentIntegTestNg {
    
    private static final String LOCAL_FILE_PATH = "inputs.html";
    private static final String SELECTOR = "input";

    @Test
    public void testMethod1() {
        goTo(UrlUtil.getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("1");
        await().until(el(SELECTOR)).value().equalTo("1");
        assertThat(el(SELECTOR).value()).isEqualTo("1");
    }

    @Test
    public void testMethod2() {
        goTo(UrlUtil.getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("2");
        await().until(el(SELECTOR)).value().equalTo("2");
        assertThat(el(SELECTOR).value()).isEqualTo("2");
    }

    @Test
    public void testMethod3() {
        goTo(UrlUtil.getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("3");
        await().until(el(SELECTOR)).value().equalTo("3");
        assertThat(el(SELECTOR).value()).isEqualTo("3");
    }

    @Test
    public void testMethod4() {
        goTo(UrlUtil.getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("4");
        await().until(el(SELECTOR)).value().equalTo("4");
        assertThat(el(SELECTOR).value()).isEqualTo("4");
    }
}
