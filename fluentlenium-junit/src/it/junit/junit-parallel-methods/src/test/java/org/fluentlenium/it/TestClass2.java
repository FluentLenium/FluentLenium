package org.fluentlenium.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

import org.junit.Test;

public class TestClass2 extends FluentIntegTest {

    private static final String LOCAL_FILE_PATH = "inputs.html";
    private static final String SELECTOR = "input";
    private static final Class<?> CLASS = TestClass2.class;

    @Test
    public void testMethod5() {
        goTo(getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("5");
        await().until(el(SELECTOR)).value().equalTo("5");
        assertThat(el(SELECTOR).value()).isEqualTo("5");
        assertThat(getTestClass()).isEqualTo(CLASS);
        assertThat(getTestMethodName()).isEqualTo("testMethod5");
    }

    @Test
    public void testMethod6() {
        goTo(getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("6");
        await().until(el(SELECTOR)).value().equalTo("6");
        assertThat(el(SELECTOR).value()).isEqualTo("6");
        assertThat(getTestClass()).isEqualTo(CLASS);
        assertThat(getTestMethodName()).isEqualTo("testMethod6");
    }

    @Test
    public void testMethod7() {
        goTo(getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("7");
        await().until(el(SELECTOR)).value().equalTo("7");
        assertThat(el(SELECTOR).value()).isEqualTo("7");
        assertThat(getTestClass()).isEqualTo(CLASS);
        assertThat(getTestMethodName()).isEqualTo("testMethod7");
    }

    @Test
    public void testMethod8() {
        goTo(getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("8");
        await().until(el(SELECTOR)).value().equalTo("8");
        assertThat(el(SELECTOR).value()).isEqualTo("8");
        assertThat(getTestClass()).isEqualTo(CLASS);
        assertThat(getTestMethodName()).isEqualTo("testMethod8");
    }
}
