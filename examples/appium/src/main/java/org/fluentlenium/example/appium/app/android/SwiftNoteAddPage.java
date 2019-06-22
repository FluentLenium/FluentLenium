package org.fluentlenium.example.appium.app.android;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;

public class SwiftNoteAddPage extends FluentPage {

    @AndroidFindBy(id = "com.moonpi.swiftnotes:id/titleEdit")
    private FluentWebElement titleElement;

    @AndroidFindBy(id = "com.moonpi.swiftnotes:id/bodyEdit")
    private FluentWebElement noteElement;

    @AndroidFindBy(xpath = "//android.widget.RelativeLayout/android.view.ViewGroup/android.widget.ImageButton")
    private FluentWebElement backButton;

    @AndroidFindBy(id = "android:id/button1")
    private FluentWebElement saveNote;

    public SwiftNoteHomePage addNote(String noteTitle, String noteBody) {
        await().until(titleElement).displayed();
        titleElement.write(noteTitle);
        noteElement.write(noteBody);
        backButton.click();
        await().until(saveNote).clickable();
        saveNote.click();
        return newInstance(SwiftNoteHomePage.class);
    }

}
