package org.fluentlenium.example.appium.app.ios;

import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;

public class HomePage extends FluentPage {

    @iOSXCUITFindBy(accessibility = "Add")
    private FluentWebElement addButton;

    @iOSXCUITFindBy(accessibility = "About")
    private FluentWebElement about;

    public AboutPage clickAboutLink() {
        await().until(about).clickable();
        about.click();
        return newInstance(AboutPage.class);
    }

    public AddPage clickAddButton() {
        addButton.click();
        return newInstance(AddPage.class);
    }

}
