package org.fluentlenium.examples.test.componentexample;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.pages.fluentlenium.MainPage;
import org.fluentlenium.examples.pages.fluentlenium.QuickStartPage;
import org.fluentlenium.examples.test.AbstractFirefoxTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

class ComponentTest extends AbstractFirefoxTest {

    @Page
    private QuickStartPage quickStartPage;

    @Page
    private MainPage mainPage;

    @BeforeEach
    void setUp() {
        getDriver().manage().window().setSize(new Dimension(1920, 1080));
    }

    @Test
    void quickStartLink() {
        mainPage.go().isAt();
        mainPage.getHeader().clickQuickstartLink().isAt();
    }

    @Test
    void homeLink() {
        quickStartPage.go().isAt();
        quickStartPage.getHeader().clickHomeLink().isAt();
    }

    @Test
    void shouldShowSlawomir() {
        mainPage.go().isAt();
        mainPage.getHeader().clickAboutLink().verifySlawomirPresence();
    }

}
