package org.fluentlenium.integration;

import static org.fluentlenium.integration.util.UrlUtil.getAbsoluteUrlFromFile;

import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

public class WaitSizeTest extends IntegrationFluentTest {
    @Test
    public void checkWithNameCssSelector() {
        goTo(getAbsoluteUrlFromFile("size-change.html"));
        await().until($(".row")).size().greaterThan(2);
    }
}
