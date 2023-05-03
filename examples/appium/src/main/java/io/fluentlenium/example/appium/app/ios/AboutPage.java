package io.fluentlenium.example.appium.app.ios;

import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.domain.FluentWebElement;

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
