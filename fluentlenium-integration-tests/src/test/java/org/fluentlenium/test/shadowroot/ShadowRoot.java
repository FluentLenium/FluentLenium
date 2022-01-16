package org.fluentlenium.test.shadowroot;

import org.fluentlenium.pages.ShadowRootPage;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unchecked")
class ShadowRoot extends IntegrationFluentTest {
    @Test
    void checkSearchWorks() throws InterruptedException {
        goTo(IntegrationFluentTest.SHADOW_URL);

        this.getDriver().getPageSource();

        Thread.sleep(2000);
        ShadowRootPage shadowRootPage = newInstance(ShadowRootPage.class);

        System.out.println(shadowRootPage.getShadowRootItemText());

    }
}
