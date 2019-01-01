package org.fluentlenium.adapter.junit;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import static org.assertj.core.api.Assertions.assertThat;

public class AfterSuccessOrderTest {
    public static class AfterOrderTestInternal extends FluentTest {

        private boolean after;
        private boolean junitAfter;

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


