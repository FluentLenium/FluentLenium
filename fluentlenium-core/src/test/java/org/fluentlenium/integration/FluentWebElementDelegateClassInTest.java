package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

public class FluentWebElementDelegateClassInTest extends IntegrationFluentTest {
    ALink linkToPage2;

    public static class ALink {
        private final WebElement webElement;

        public ALink(final WebElement webElement) {
            this.webElement = webElement;
        }

        public void clickIfDisplayed() {
            if (webElement.isDisplayed()) {
                webElement.click();
            }
        }
    }

    @Test
    public void when_web_element_in_test_then_they_are_instanciated() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        linkToPage2.clickIfDisplayed();
        assertThat(url()).isEqualTo(IntegrationFluentTest.PAGE_2_URL);
    }
}
