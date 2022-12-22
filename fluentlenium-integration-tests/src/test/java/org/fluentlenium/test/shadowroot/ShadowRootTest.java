package org.fluentlenium.test.shadowroot;

import org.fluentlenium.pages.ShadowRootComponent;
import org.fluentlenium.pages.ShadowRootPage;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShadowRootTest extends IntegrationFluentTest {
    @Test
    void checkSearchWorks() {
        goTo(IntegrationFluentTest.SHADOW_URL);

        this.getDriver().getPageSource();

        ShadowRootPage shadowRootPage = newInstance(ShadowRootPage.class);

        assertThat(shadowRootPage.getShadowRootItemText()).contains("Inside Shadow DOM");
    }

    @Test
    void checkSearchWorksForWebComponent() {
        goTo(IntegrationFluentTest.SHADOW_URL);

        this.getDriver().getPageSource();

        ShadowRootComponent shadowRootWebComponent = el("body").as(ShadowRootComponent.class);

        assertThat(shadowRootWebComponent.getShadowRootItemText()).contains("Inside Shadow DOM");
    }
}
