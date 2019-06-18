package org.fluentlenium.example.appium.app.ios;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class UITestDemoAppAboutPage extends FluentPage {

    @FindBy(xpath = "//XCUIElementTypeButton[@name='Learn more']")
    private FluentWebElement learnMoreButton;

    public void verifyIfIsLoaded() {
        await().until(learnMoreButton).displayed();
    }

}
