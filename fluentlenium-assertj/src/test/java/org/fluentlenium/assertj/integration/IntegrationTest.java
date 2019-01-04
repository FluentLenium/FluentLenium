package org.fluentlenium.assertj.integration;

import org.fluentlenium.adapter.FluentStandalone;
import org.junit.After;
import org.junit.Before;

import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

public abstract class IntegrationTest {

    protected static final String DEFAULT_URL = getAbsoluteUrlFromFile("index.html");

    protected FluentStandalone standalone = new FluentStandalone();

    @Before
    public void before() {
        standalone.init();
    }

    @After
    public void quit() {
        standalone.quit();
    }

}
