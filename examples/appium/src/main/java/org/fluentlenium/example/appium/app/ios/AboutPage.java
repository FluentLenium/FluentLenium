package org.fluentlenium.example.appium.app.ios;

import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;

public class AboutPage extends FluentPage {

    @iOSXCUITFindBy(accessibility = "Learn more")
    private FluentWebElement learnMoreButton;

    public void verifyIfIsLoaded() {
        await().until(learnMoreButton).present();
    }

}
