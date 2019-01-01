package org.fluentlenium.adapter.junit;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for <a href="https://github.com/FluentLenium/FluentLenium/issues/390">Issue #390</a>.
 */
public class AfterFailureOrderTest {
    public static class AfterOrderTestInternal extends FluentTest {

        private boolean after;
        private boolean junitAfter;

        @Override
        public WebDriver newWebDriver() {
            return new HtmlUnitDriver();
        }

        @After
        public void after() {
            after = true;
        }

        @org.junit.After
        public void junitAfter() {
            junitAfter = true;
        }

        @Before
        public void before() {
            after = false;
            junitAfter = false;
        }

        @Test
        public void test() {
            el(".inexistant").now();
        }

        @Override
        protected void finished(Class<?> testClass, String testName) {
            Assertions.assertThat(after).isTrue();
            Assertions.assertThat(junitAfter).isTrue();
        }

        @Override
        protected void failed(Throwable e, Class<?> testClass, String testName) {
            Assertions.assertThat(after).isFalse();
            Assertions.assertThat(junitAfter).isTrue();
        }
    }

    @Test
    public void testFluentTest() {
        Result result = JUnitCore.runClasses(AfterOrderTestInternal.class);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getException()).isInstanceOf(NoSuchElementException.class);
    }
}


