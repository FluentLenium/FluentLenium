package org.fluentlenium.example.appium.app.android;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class SwiftNoteHomePage extends FluentPage {

    @AndroidFindBy(id = "com.moonpi.swiftnotes:id/newNote")
    private FluentWebElement plusButton;

    @AndroidFindBy(id = "com.moonpi.swiftnotes:id/titleEdit")
    private FluentWebElement titleElement;

    @AndroidFindBy(id = "com.moonpi.swiftnotes:id/bodyEdit")
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
