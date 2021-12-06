package org.fluentlenium.example.appium.app.ios;

import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;

public class AboutPage extends FluentPage {

    @iOSXCUITFindBy(accessibility = "Learn more")
    private FluentWebElement learnMoreButton;

    @iOSXCUITFindBy(accessibility = "Browse")
    private FluentWebElement browse;

    public void verifyIfIsLoaded() {
        await().until(learnMoreButton).present();
    }

    public HomePage clickBrowseButton() {
        browse.click();
        return newInstance(HomePage.class);
    }

}
