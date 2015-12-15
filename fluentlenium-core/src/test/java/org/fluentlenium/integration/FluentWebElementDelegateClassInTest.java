package org.fluentlenium.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class FluentWebElementDelegateClassInTest extends LocalFluentCase {
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
        goTo(LocalFluentCase.DEFAULT_URL);
        linkToPage2.clickIfDisplayed();
        assertThat(url()).isEqualTo(LocalFluentCase.PAGE_2_URL);
    }
}
