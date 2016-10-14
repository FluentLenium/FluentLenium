package org.fluentlenium.adapter.testng.integration;

import org.assertj.core.api.Assertions;
import org.fluentlenium.adapter.testng.integration.localtest.IntegrationFluentTestNg;
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
    protected void finished(final Class<?> testClass, final String testName) {
        Assertions.assertThat(after).isFalse();
    }

    @Override
    protected void failed(final Throwable e, final Class<?> testClass, final String testName) {
        Assertions.assertThat(after).isFalse();
    }
}
