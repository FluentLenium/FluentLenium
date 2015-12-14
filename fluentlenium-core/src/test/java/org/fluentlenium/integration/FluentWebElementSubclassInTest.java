package org.fluentlenium.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class FluentWebElementSubclassInTest extends LocalFluentCase {
    ALink linkToPage2;

    public static class ALink extends FluentWebElement {
        public ALink(final WebElement webElement) {
            super(webElement);
        }

        public void clickIfDisplayed() {
            if (isDisplayed()) {
                click();
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
