package org.fluentlenium;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AutomaticOnFailTest extends IntegrationFluentTestNg {

    private boolean after;

    @AfterTest
    public void after() {
        after = true;
    }

    @BeforeTest
    public void before() {
        after = false;
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void test() {
        el(".inexistant").now();
    }

    @Override
    protected void finished(Class<?> testClass, String testName) {
        Assertions.assertThat(after).isFalse();
    }

    @Override
    protected void failed(Throwable e, Class<?> testClass, String testName) {
        Assertions.assertThat(after).isFalse();
    }
}
