package io.fluentlenium.adapter.junit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for <a href="https://github.com/FluentLenium/FluentLenium/issues/390">Issue #390</a>.
 */
public class AfterFailedTest {
    public static class AfterOrderTestInternal extends FluentTest {

        private boolean after;
        private boolean junitAfter;

        @BeforeClass
        public static void setUpChrome() {
            WebDriverManager.chromedriver().setup();
        }

        @Override
        public WebDriver newWebDriver() {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless=new");
            return new ChromeDriver(chromeOptions);
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


