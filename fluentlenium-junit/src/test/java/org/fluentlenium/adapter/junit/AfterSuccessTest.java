package org.fluentlenium.adapter.junit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import static org.assertj.core.api.Assertions.assertThat;

public class AfterSuccessTest {
    public static class AfterOrderTestInternal extends FluentTest {

        private boolean after;
        private boolean junitAfter;

        @BeforeClass
        public static void setUpChrome() {
            WebDriverManager.chromedriver().setup();
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
        }

        @Override
        protected void finished(Class<?> testClass, String testName) {
            Assertions.assertThat(after).isTrue();
            Assertions.assertThat(junitAfter).isTrue();
        }

    }

    @Test
    public void testFluentTest() {
        Result result = JUnitCore.runClasses(AfterOrderTestInternal.class);
        assertThat(result.getFailures()).isEmpty();
    }
}
