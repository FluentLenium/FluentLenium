package org.fluentlenium.test.fluentwebelement;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

class FluentWebElementSubclassInTest extends IntegrationFluentTest {
    private ALink linkToPage2;

    public static class ALink extends FluentWebElement {
        ALink(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
            super(webElement, fluentControl, instantiator);
        }

        void clickIfDisplayed() {
            if (displayed()) {
                click();
            }
        }
    }

    @Test
    void whenWebElementInTestThenTheyAreInstantiated() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        linkToPage2.clickIfDisplayed();
        assertThat(url()).contains("page2.html");
    }
}
