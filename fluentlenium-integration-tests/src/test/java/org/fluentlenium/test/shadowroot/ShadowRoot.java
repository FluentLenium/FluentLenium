package org.fluentlenium.test.shadowroot;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Unshadow;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unchecked")
class ShadowRoot extends IntegrationFluentTest {


    @Test
    void checkSearchWorks() throws InterruptedException {
        goTo(SHADOW_URL);
        ShadowRootPage shadowRootPage = newInstance(ShadowRootPage.class);

        System.out.println(shadowRootPage.getShadowRootItemText());

//        System.out.println(insideShadowRoot.text());

    }

    class ShadowRootPage extends FluentPage {
        @Unshadow(css = "#inside")
        FluentWebElement inside;

        public String getShadowRootItemText() throws InterruptedException {
            Thread.sleep(1000);
            return inside.text();
        }
    }
}
