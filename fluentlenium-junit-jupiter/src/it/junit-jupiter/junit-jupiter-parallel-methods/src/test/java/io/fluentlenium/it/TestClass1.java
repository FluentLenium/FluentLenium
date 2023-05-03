package io.fluentlenium.it;

import org.junit.jupiter.api.Test;

import static io.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;
import static org.assertj.core.api.Assertions.assertThat;

class TestClass1 extends FluentIntegTest {
    private static final String LOCAL_FILE_PATH = "inputs.html";
    private static final String SELECTOR = "input";
    private static final Class<?> CLASS = TestClass1.class;

    @Test
    void testMethod1() {
        goTo(getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("1");
        await().until(el(SELECTOR)).value().equalTo("1");
        assertThat(el(SELECTOR).value()).isEqualTo("1");
        assertThat(getTestClass()).isEqualTo(CLASS);
        assertThat(getTestMethodName()).isEqualTo("testMethod1");
    }

    @Test
    void testMethod2() {
        goTo(getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("2");
        await().until(el(SELECTOR)).value().equalTo("2");
        assertThat(el(SELECTOR).value()).isEqualTo("2");
        assertThat(getTestClass()).isEqualTo(CLASS);
        assertThat(getTestMethodName()).isEqualTo("testMethod2");
    }

    @Test
    void testMethod3() {
        goTo(getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("3");
        await().until(el(SELECTOR)).value().equalTo("3");
        assertThat(el(SELECTOR).value()).isEqualTo("3");
        assertThat(getTestClass()).isEqualTo(CLASS);
        assertThat(getTestMethodName()).isEqualTo("testMethod3");
    }

    @Test
    void testMethod4() {
        goTo(getAbsoluteUrlFromFile(LOCAL_FILE_PATH));
        el(SELECTOR).fill().with("4");
        await().until(el(SELECTOR)).value().equalTo("4");
        assertThat(el(SELECTOR).value()).isEqualTo("4");
        assertThat(getTestClass()).isEqualTo(CLASS);
        assertThat(getTestMethodName()).isEqualTo("testMethod4");
    }
}
