package org.fluentlenium.integration;

import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

public class FluentWebElementSubclassInTest extends LocalFluentCase {
    ALink linkToPage2;

    public static class ALink extends FluentWebElement {
        public ALink(WebElement webElement, WebDriver driver, ComponentInstantiator instantiator) {
            super(webElement, driver, instantiator);
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
