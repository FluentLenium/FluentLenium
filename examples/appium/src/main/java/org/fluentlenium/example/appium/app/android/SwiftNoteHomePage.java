package org.fluentlenium.example.appium.app.android;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class SwiftNoteHomePage extends FluentPage {

    @FindBy(id = "com.moonpi.swiftnotes:id/newNote")
    private FluentWebElement plusButton;

    @FindBy(id = "com.moonpi.swiftnotes:id/titleEdit")
    private FluentWebElement titleElement;

    @FindBy(id = "com.moonpi.swiftnotes:id/bodyEdit")
    private FluentWebElement noteElement;

    public SwiftNoteHomePage addNote(String noteTitle, String noteBody) {
        plusButton.click();
        await().until(titleElement).displayed();
        titleElement.write(noteTitle);
        noteElement.write(noteBody);
        return this;
    }

    public void verifyNote(String noteTitle, String noteBody) {
        assertThat(titleElement).hasText(noteTitle);
        assertThat(noteElement).hasText(noteBody);
    }

}
