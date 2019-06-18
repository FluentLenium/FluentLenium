package org.fluentlenium.example.appium.android;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.example.appium.ExampleFluentTest;
import org.fluentlenium.example.appium.app.android.SwiftNoteHomePage;
import org.junit.Test;

public class AndroidSwiftNotesApp extends ExampleFluentTest {

    @Page
    private SwiftNoteHomePage noteApp;

    @Test
    public void shouldCorrectlyAddNote() {
        noteApp
                .addNote("SampleTitle", "SampleBody")
                .verifyNote("SampleTitle", "SampleBody");
    }

}

