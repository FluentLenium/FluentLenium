package org.fluentlenium.example.appium.app.ios;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class UITestDemoAppHomePage extends FluentPage {

    @FindBy(xpath = "//XCUIElementTypeButton[@name='About']")
    private FluentWebElement about;

    public UITestDemoAppAboutPage clickAboutLink() {
        about.click();
        return newInstance(UITestDemoAppAboutPage.class);
    }

}
