package io.fluentlenium.example.appium.app.ios;

import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.domain.FluentWebElement;

public class AddPage extends FluentPage {

    @iOSXCUITFindBy(accessibility = "Save")
    private FluentWebElement saveButton;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTextField")
    private FluentWebElement noteNameSelector;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther/XCUIElementTypeOther[4]/XCUIElementTypeTextView")
    private FluentWebElement noteDescriptionSelector;

    public HomePage addName(String noteName, String noteDescription) {
        await().until(noteNameSelector).displayed();

        noteNameSelector.fill().with(noteName);
        noteDescriptionSelector.clear().fill().with(noteDescription);
        saveButton.click();
        return newInstance(HomePage.class);
    }

}
