package org.fluentlenium.integration;


import org.fluentlenium.integration.localtest.SauceLabsFluentCase;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultWaitOnLabsTest extends SauceLabsFluentCase {
    @Before
    public void before() {
        goTo(DEFAULT_URL);

    }

    @Override
    public WebDriver getDefaultDriver() {
        return new FirefoxDriver();
    }

    @Override
    public void setDefaultConfig() {
        withDefaultSearchWait(20, TimeUnit.SECONDS);
    }

    @Test
    public void when_default_search_wait_then_implicit_wait() {
        goTo(JAVASCRIPT_URL);
        assertThat(find("#newField")).hasSize(1);
    }


}
