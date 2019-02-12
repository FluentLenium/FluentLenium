package org.fluentlenium.test.fluentwebelement;

import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

class FluentWebElementDelegateClassInTest extends IntegrationFluentTest {
    private ALink linkToPage2;

    public static class ALink {
        private final WebElement webElement;

        ALink(WebElement webElement) {
            this.webElement = webElement;
        }

        void clickIfDisplayed() {
            if (webElement.isDisplayed()) {
                webElement.click();
            }
        }
    }

    @Test
    void whenWebElementInTestThenTheyAreInstantiated() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        linkToPage2.clickIfDisplayed();
        assertThat(url()).isEqualTo(IntegrationFluentTest.PAGE_2_URL);
    }
}
