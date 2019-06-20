package org.fluentlenium.example.appium.app.ios;

import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;

public class UITestDemoAppHomePage extends FluentPage {

    @iOSXCUITFindBy(accessibility = "About")
    private FluentWebElement about;

    public UITestDemoAppAboutPage clickAboutLink() {
        about.click();
        return newInstance(UITestDemoAppAboutPage.class);
    }

}
